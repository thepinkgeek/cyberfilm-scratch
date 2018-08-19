package com.cyberfilms.cyberfilms.controllers

import com.google.common.base.Strings
import org.apache.commons.io.FileUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

import static org.apache.commons.io.IOUtils.toInputStream

@Service
class SourceCodeBuilder {
    private static final String CMAKELIST_FILE = "cmakelists"
    private static final String MAIN_FILE = "maincpp"
    private static final String NEW_LINE = "\n"
    private final String destinationDirectory
    private final String sourceDirectory
    private final Properties properties;

    @Autowired
    AlgorithmFactory algorithmFactory

    @Autowired
    TemplateEngine textTemplateEngine

    @Autowired
    Command cmakeCommand

    @Autowired
    Command makeCommand

    @Autowired
    ProcessExecutor processExecutor

    SourceCodeBuilder() {
        properties = new Properties()
        loadProperties()

        println("properties = " + this.properties.keySet())
        println("properties = " + this.properties.getProperty("destinationDirectory"))
        println("properties = " + this.properties.getProperty("sourceDirectory"))

        final String destinationDirectoryProperty =
                Strings.nullToEmpty(this.properties.getProperty("destinationDirectory"))
        this.destinationDirectory =
                destinationDirectoryProperty.empty ?
                        this.properties.getProperty("user.home") :
                        destinationDirectoryProperty

        final String sourceDirectoryProperty =
                Strings.nullToEmpty(this.properties.getProperty("sourceDirectory"))
        this.sourceDirectory = sourceDirectoryProperty.empty ?
                System.getProperty("user.home") :
                sourceDirectoryProperty
    }

    private void loadProperties() {
        println("loading properties")
        InputStream inputStream = new ClassPathResource("application.properties").inputStream
        this.properties.load(inputStream)
        println("done loading properties")
    }

    boolean build(final Project project) {
        prepareSourcesAndHeaders(project)
        convert(project)
        buildSources(project)
    }

    private Context setupContext(SourceCode sourceCode) {
        final Context context = new Context()
        sourceCode.variables.each { key, value -> context.setVariable(key, value) }

        Set<String> includeFileDependencies = new LinkedHashSet<>()
        Set<Algorithm> dependencies = this.algorithmFactory.getAlgorithm(sourceCode.name).dependencies

        dependencies.each {
            dependency ->
                includeFileDependencies.add("${dependency.schemeName}/${dependency.name}/includes/${dependency.name}.h")
        }

        includeFileDependencies.add("${sourceCode.schemeName}/${sourceCode.name}/includes/${sourceCode.name}.h")
        context.setVariable("includeFiles", includeFileDependencies)
        return context
    }

    private Context setupBuildContext(final Project project) {
        SourceCode sourceCode = project.sourceCode
        Context context = new Context()
        println("project name = " + project.name)
        context.setVariable("projectName", project.name)

        Set<String> buildDependencies = [] as Set
        Set<Algorithm> dependencies = retrieveDependencies(this.algorithmFactory.getAlgorithm(sourceCode.name))
        println("all retrieved dependencies " + dependencies)

        dependencies.each { dependency ->
            buildDependencies += "${dependency.schemeName}/${dependency.name}"
        }
        context.setVariable("dependencies", buildDependencies)

        return context
    }

    private Set<Algorithm> retrieveDependencies(Algorithm algorithm) {
        def allDependencies = [] as Set
        algorithm.dependencies.each {
            dependency ->
                allDependencies += retrieveDependencies(dependency)
        }
        allDependencies += algorithm
        allDependencies
    }

    private void prepareSourcesAndHeaders(Project project) {
        SourceCode sourceCode = project.sourceCode
        Set<Algorithm> dependencies = retrieveDependencies(this.algorithmFactory.getAlgorithm(sourceCode.name))
        final String projectDestinationDirectory = """${this.destinationDirectory}/${project.name}"""
        File theDirectory = new File(projectDestinationDirectory)
        theDirectory.mkdirs()

        dependencies.each { dependency ->
            FileUtils.copyDirectory(
                    new File("""${this.sourceDirectory}/${dependency.schemeName}/${dependency.name}"""),
                    new File("""${this.destinationDirectory}/${project.name}/${dependency.schemeName}/${dependency.name}""")
            )
        }

        FileUtils.copyDirectory(
                new File("""${this.sourceDirectory}/${sourceCode.schemeName}/${sourceCode.name}"""),
                new File("""${this.destinationDirectory}/${project.name}/${sourceCode.schemeName}/${sourceCode.name}""")
        )
    }

    boolean buildSources(final Project project) {
        File theDirectory = new File("${this.destinationDirectory}/${project.name}/build")
        theDirectory.mkdirs()

        processExecutor.executeCommand(cmakeCommand, theDirectory) &&
                processExecutor.executeCommand(makeCommand, theDirectory)
    }

    private void createFile(final InputStream inputStream, String directory, String fileName) {
        inputStream.withCloseable {
            final String fileDestinationDirectory = """${this.destinationDirectory}/${directory}"""
            File theDirectory = new File(fileDestinationDirectory)
            theDirectory.mkdirs()

            FileOutputStream outputStream =
                    new FileOutputStream(new File("""${fileDestinationDirectory}/${fileName}"""))
            outputStream.withCloseable {
                inputStream.eachLine {
                    line ->
                        outputStream.write(line.bytes)
                        outputStream.write(NEW_LINE.bytes)
                }
            }
        }
    }

    private void convert(final Project project) {
        SourceCode sourceCode = project.sourceCode
        System.out.println(textTemplateEngine.getTemplateResolvers()[0].getProperties())
        String mainCppText = textTemplateEngine.process(
                """${sourceCode.name}$MAIN_FILE""",
                setupContext(sourceCode))
        InputStream mainCppInput = toInputStream(mainCppText, "UTF-8")
        createFile(mainCppInput,"${project.name}","main.cpp")

        String cmakeListsText = textTemplateEngine.process(
                CMAKELIST_FILE,
                setupBuildContext(project))
        InputStream cmakeListInput = toInputStream(cmakeListsText, "UTF-8")
        createFile(cmakeListInput,
                "${project.name}",
                "CMakeLists.txt")
    }
}
