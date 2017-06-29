package com.github.vadeg.gradle.exhibitor.it.client

import com.github.vadeg.gradle.exhibitor.client.ExhibitorClient
import com.github.vadeg.gradle.exhibitor.client.Result
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDateTime

class ExhibitorClientTests
        extends Specification {

    ExhibitorClient client

    def setup() {
        client = new ExhibitorClient("http://localhost:8181")
    }

    def "Failed to create node because of empty path in the middle"() {
        when:
        def result = client.upsert("a//b", null)
        then:
        result instanceof Result
        result.succeed == false
        result.message.contains("Invalid path string \"/a//b\"")
    }

    def "Path starts with /"() {
        when:
        client.upsert("/a/b", null)

        then:
        thrown(IllegalArgumentException.class)
    }

    @Unroll
    def "Create \"#path\" with value \"#data\" should return \"#message\""() {
        expect:
        def result = client.upsert(path, data)

        result instanceof Result
        assert result.succeed
        result.message == message

        cleanup:
        client.delete(path)

        where:
        path         | data                   | message
        "test/a/b/c" | "This is a test data" || "OK"
        "test/a"     | 123                   || "OK"
        "test/empty" | null                  || "OK"
        "test/abcde" | 'c'                   || "OK"
        "test/time"  | LocalDateTime.now()   || "OK"
    }

    def "Create and delete node"() {
        given:
        def path = "delete-test-path"

        when:
        client.upsert(path, null)
        def result = client.delete(path)

        then:
        result instanceof Result
        result.succeed == true
        result.message.contains("OK")

        cleanup:
        client.delete(path)

    }

    def "Delete non-existing path"() {
        given:
        def path = "unknown-path"
        when:
        def result = client.delete(path)
        then:
        result instanceof Result
        result.succeed == false
        result.message == "KeeperErrorCode = NoNode for /unknown-path"
    }

    @Unroll
    def "Check that #data is saved successfully"() {
        expect:
        client.upsert(path, data)
        def actualData = client.get(path)

        data.toString() == actualData

        cleanup:
        client.delete(path)

        where:
        path         | data
        "test/a/b/c" | "This is a test data"
        "test/a"     | 123
        "test/abcde" | 'c'
        "test/time"  | LocalDateTime.now()
    }

    def "Check null data is saved"() {
        given:
        def path = "test/null-data"

        when:
        client.upsert(path)
        def actualData = client.get(path)

        then:
        actualData == ""

        cleanup:
        client.delete(path)
    }
}
