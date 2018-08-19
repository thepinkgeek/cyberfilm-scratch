package com.cyberfilms.cyberfilms.controllers

import com.cyberfilms.cyberfilms.repositories.FrameRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class FrameCreationAutomationController {

    @Autowired
    FrameRepository repository

    @RequestMapping(value = "/createframes", method = RequestMethod.PUT)
    Map<String, String> createShortestPathFrames(@RequestBody FrameInfo frameInfo) {
        println "hello!"
        return [:]
    }

}
