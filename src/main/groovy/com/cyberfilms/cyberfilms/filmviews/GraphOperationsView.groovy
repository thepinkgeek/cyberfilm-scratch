package com.cyberfilms.cyberfilms.filmviews

class GraphOperationsView {

    static void neighborTraversal(PriorityQueue<Node> queue,
                           int[][] graph,
                           boolean[] visitedList,
                           Closure operation,
                           Object context) {

        Node node = queue.peek()

        if (node) {
            int index = node.getNumber()
            addNeighbors(queue, graph, index, visitedList, operation, context)
            visitedList[index] = true
        }

    }

    static void addNeighbors(PriorityQueue<Node> queue,
                      int[][] graph,
                      int index,
                      boolean[] visitedList,
                      Closure operation,
                      Object context) {

        int[] neighbors = graph[index]

        for (int i = 0; i < neighbors.length; i++) {
            int distance = neighbors[i]

            if (i != index && distance != 0 && !visitedList[i]) {
                Node node = new Node(i, distance, index)
                operation(context, node)
                queue.add(node)
            }
        }
    }
}
