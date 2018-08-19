package com.cyberfilms.cyberfilms.controllers

import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class FrameViewController {

    @RequestMapping(value = "/simulate", method = RequestMethod.GET)
    Map<String, String> simulate(@RequestParam int frameId) {
        [:]
    }
}
