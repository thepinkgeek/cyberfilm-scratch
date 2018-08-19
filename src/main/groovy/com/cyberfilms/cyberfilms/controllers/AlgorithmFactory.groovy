package com.cyberfilms.cyberfilms.controllers

class AlgorithmFactory {
    private final Map<String, Algorithm> algorithmMap

    public AlgorithmFactory(final Map<String, Algorithm> algorithmMap) {
        this.algorithmMap = algorithmMap
    }

    public Algorithm getAlgorithm(final String key) {
        return this.algorithmMap.get(key)
    }
}
