package com.cyberfilms.cyberfilms.entities

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Frame implements Serializable {
    @Id
    ObjectId id
    String name
    int frameId
    List<Node> nodes
    List<Link> links

    static class Node {
        String id
        String label
        String shape
    }

    static class Link {
        String source
        String target
        String label
    }

}
