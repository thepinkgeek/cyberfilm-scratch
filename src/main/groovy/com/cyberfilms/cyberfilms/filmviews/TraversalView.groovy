package com.cyberfilms.cyberfilms.filmviews

class TraversalView {

   static void doTraversal(int[][] graph,
                    int numVertices,
                    int startIndex,
                    Closure operation,
                    Object context)  {

      Node node = Node(startIndex, 0, 0)
      PriorityQueue<Node> priorityQueue = new PriorityQueue<>()
      boolean[] isVisited = new boolean[numVertices]
      priorityQueue.add(node)

      while (!priorityQueue.isEmpty()) {
         GraphOperationsView.neighborTraversal(priorityQueue,
                 graph,
                 isVisited,
                 operation,
                 context)
      }
   }
}
