package com.cyberfilms.cyberfilms

import org.springframework.stereotype.Service

import java.util.concurrent.Executors
import java.util.function.Consumer

@Service
class ProcessExecutor {

    boolean executeCommand(final Command command, final File directory) {
        ProcessBuilder builder = new ProcessBuilder()

        println(getOSName())
        if (isOS("linux")) {
            builder.command(command.commandList)
            builder.directory(directory)
            Process process = builder.start()
            StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), new StreamOutputConsumer())
            Executors.newSingleThreadExecutor().submit(streamGobbler)
            int exitCode = process.waitFor()

            return exitCode == 0
        }
        return false
    }

    private String getOSName() {
        return System.getProperty("os.name").toLowerCase()
    }

    private boolean  isOS(final String name) {
       return getOSName().startsWith(name)
    }

    private static class StreamGobbler implements Runnable {
        private InputStream inputStream
        private Consumer<String> consumer

        StreamGobbler (InputStream inputStream, Consumer<String> consumer) {
            this.inputStream = inputStream
            this.consumer = consumer
        }

        @Override
        void run() {
           new BufferedReader(new InputStreamReader(inputStream)).lines()
            .forEach(consumer)
        }
    }

    private static class StreamOutputConsumer implements Consumer<String> {

        @Override
        void accept(String s) {
            println(s)
        }
    }
}
