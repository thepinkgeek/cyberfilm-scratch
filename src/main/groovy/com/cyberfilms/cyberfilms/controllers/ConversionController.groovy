package com.cyberfilms.cyberfilms.controllers

import com.cyberfilms.cyberfilms.entities.Film
import com.cyberfilms.cyberfilms.repositories.FilmRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class ConversionController {

    @Autowired
    SourceCodeBuilder sourceCodeBuilder

    @Autowired
    FilmRepository filmRepository

    private Map<String, String> buildSourceCode(Film film) {
        if (sourceCodeBuilder.build(new Project(film.projectName,
                new SourceCode(film.schemeName, film.name, film.variables)))) {
            return ["result" : "Success"]
        } else {
            return ["result" : "Failure"]
        }
    }

    @RequestMapping(value = "/build", method = RequestMethod.PUT)
    Map<String, String> build(@RequestBody BuildParams buildParams) {
        println("projectName = " + buildParams.projectName)

        filmRepository.findByProjectName(buildParams.projectName).ifPresent({
            film ->
                return buildSourceCode(film)
        })
        return ["result" : "Failure"]
    }

    @RequestMapping(value = "/saveandbuild", method = RequestMethod.PUT)
    Map<String, String> saveAndBuild(@RequestBody Film film) {
        Film newFilm = filmRepository.save(film)
        return buildSourceCode(newFilm)
    }
}
