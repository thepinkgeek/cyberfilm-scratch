package com.cyberfilms.cyberfilms.controllers

class Algorithm {
    final String schemeName
    final String name
    final Set<Algorithm> dependencies

    Algorithm(String schemeName, String name, Set<Algorithm> dependencies) {
        this.schemeName = schemeName
        this.name = name
        this.dependencies = dependencies
    }

    @Override
    String toString() {
        return "${this.schemeName}/${this.name}"
    }
}
