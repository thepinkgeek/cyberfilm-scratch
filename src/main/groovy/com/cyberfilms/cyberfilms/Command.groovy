package com.cyberfilms.cyberfilms

class Command {
    final String command
    final List<String> parameters

    Command(final String command, final List<String> parameters) {
        this.command = command
        this.parameters = parameters
    }

    List<String> getCommandList() {
        return [command] + parameters
    }
}
