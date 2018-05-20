package com.cyberfilms.cyberfilms.filmviews

class Node implements Comparable<Node> {
    int number
    int distance
    int parentIndex

    Node(int number, int distance, int index) {
        this.number = number
        this.distance = distance
        this.parentIndex = index
    }

    @Override
    int compareTo(Node other) {
        if (isLessThan(other)) {
            return -1
        } else if (isEqual(other)) {
            return 0
        }
        return 1
    }

    private boolean hasSameLevel(Node other) {
        return number == other.number
    }

    private boolean isLessThan(Node other) {
        if (hasSameLevel(other)) {
            return distance < other.distance
        } else {
            return number < other.number
        }
    }

    private boolean isEqual(Node other) {
        if (hasSameLevel(other)) {
            return distance == other.distance
        } else {
            return false
        }
    }
}
