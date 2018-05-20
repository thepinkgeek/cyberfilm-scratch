package com.cyberfilms.cyberfilms

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Film implements Serializable {
    @Id
    String projectName
    String schemeName
    String name
    Map<String, Object> variables
}
