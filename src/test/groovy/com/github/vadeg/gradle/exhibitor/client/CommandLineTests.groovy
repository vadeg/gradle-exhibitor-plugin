package com.github.vadeg.gradle.exhibitor.client

import spock.lang.Specification
import spock.lang.Unroll

import static com.github.vadeg.gradle.exhibitor.client.CommandLine.Type.CREATE
import static com.github.vadeg.gradle.exhibitor.client.CommandLine.Type.DELETE

class CommandLineTests
        extends Specification {

    @Unroll
    def "Parse \"#line\""() {
        expect:
        def commandLine = CommandLine.parse(line)
        commandLine.command == command
        commandLine.path == path
        commandLine.value == value

        where:
        line                                        | command | path    | value
        "create a true"                             | CREATE  | "a"     | "true"
        "create a/b 12345"                          | CREATE  | "a/b"   | "12345"
        "create a/b this is a string"               | CREATE  | "a/b"   | "this is a string"
        "create a/b \"this is a string in quotes\"" | CREATE  | "a/b"   | "this is a string in quotes"
        "create a/b"                                | CREATE  | "a/b"   | null
        "delete a/b"                                | DELETE  | "a/b"   | null
        "delete a "                                 | DELETE  | "a"     | null
        "create      a/b/c      123"                | CREATE  | "a/b/c" | "123"
    }

    @Unroll
    def "Failed to parse #name"() {
        expect:
        def commandLine = CommandLine.parse(line)
        commandLine == null

        where:
        name                     | line
        "empty line"             | ""
        "null line"              | null
        "# this is comment line" | null
    }

    def "Single command parsing should fail"() {
        when:
        CommandLine.parse("create")

        then:
        thrown(IllegalArgumentException.class)
    }

    def "Parse unknown command should fail"() {
        when:
        CommandLine.parse("upsert /a/b/c 1")

        then:
        thrown(IllegalArgumentException.class)
    }


}
