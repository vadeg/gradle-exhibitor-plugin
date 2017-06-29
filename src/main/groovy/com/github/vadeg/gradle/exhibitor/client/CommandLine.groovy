package com.github.vadeg.gradle.exhibitor.client

class CommandLine {

    static final def SEPARATOR = " "

    final def command

    final def path

    final def value

    static enum Type {
        CREATE,
        DELETE

        static def parse(str) {
            return values().find { item -> item.name().equalsIgnoreCase(str) }
        }
    }

    CommandLine(command, path, value) {
        this.command = command
        this.path = path
        this.value = value
    }

    static def parse(String commandLine) {
        def line = commandLine?.trim()?.replaceAll("\\s+", " ")
        if (!line) {
            return null
        }
        def values = line.split(SEPARATOR)
        def size = values.size()
        if (size == 0) {
            return null
        } else if (size == 1) {
            throw new IllegalArgumentException("only command ${values[0]} provided")
        }
        def command = Type.parse(values[0]?.trim())
        if (!command) {
            throw new IllegalArgumentException("Unknown command \"${values[0]}\"")
        }
        def path = values[1]?.trim()
        def value = size >= 3 ? values.drop(2).join(SEPARATOR) : null
        value = trimQuotes(value)
        new CommandLine(command, path, value)
    }

    private static def trimQuotes(str) {
        if (str?.startsWith("\"") && str?.endsWith("\"")) {
            str.substring(1, str.length() - 1)
        } else {
            str
        }
    }


    @Override
    String toString() {
        return """\
            CommandLine{
                command=$command,
                path=$path,
                value=$value
            }"""
    }


}
