package com.cyberfilms.cyberfilms

class SourceCode {
    final String schemeName
    final String name
    Map<String, Object> variables

    SourceCode(final String schemeName, final String name, final Map<String, Object> variables) {
        this.schemeName = schemeName
        this.name = name
        this.variables = variables
    }
}
