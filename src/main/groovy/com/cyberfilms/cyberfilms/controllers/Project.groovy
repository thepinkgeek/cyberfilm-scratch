package com.cyberfilms.cyberfilms.controllers

class Project {
    final String name
    final SourceCode sourceCode

    Project(final String name, final SourceCode sourceCode) {
        this.name = name
        this.sourceCode = sourceCode
    }
}