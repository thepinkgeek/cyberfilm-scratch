package com.cyberfilms.cyberfilms.controllers

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AlgorithmData {
    private static
    final Algorithm GENERAL_GRAPH_ALGORITHM = new Algorithm("Graph", "General", [] as Set)
    private static
    final Algorithm GENERAL_GRAPH_TRAVERSAL_ALGORITHM =
            new Algorithm("Graph", "Traversal", [GENERAL_GRAPH_ALGORITHM] as Set)

    Algorithm djikstraAlgorithm() {
        return new Algorithm("Graph", "Djikstra", [GENERAL_GRAPH_TRAVERSAL_ALGORITHM] as Set)
    }


    @Bean("algorithmFactory")
    AlgorithmFactory getAlgorithmFactory() {
        Map<String, Algorithm> algorithmMap = ["Djikstra" : djikstraAlgorithm()] as Map<String, Algorithm>
        return new AlgorithmFactory(algorithmMap)
    }
}
