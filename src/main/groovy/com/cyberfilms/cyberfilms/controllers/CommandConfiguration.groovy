package com.cyberfilms.cyberfilms.controllers

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CommandConfiguration {
    @Bean("cmakeCommand")
    Command cmakeCommand() {
        return new Command("cmake", ["../"])
    }

    @Bean("makeCommand")
    Command makeCommand() {
        return new Command("make", [])
    }
}
