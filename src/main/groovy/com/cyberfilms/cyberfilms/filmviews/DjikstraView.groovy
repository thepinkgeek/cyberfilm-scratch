package com.cyberfilms.cyberfilms.filmviews

class DjikstraView {

    static void djikstraOperation(Object context, Node node) {
        DjikstraContext djikstraContext = (DjikstraContext) context
        long candidate = djikstraContext.distances[node.parentIndex]
        long current = djikstraContext.distances[node.number]
    }
}
