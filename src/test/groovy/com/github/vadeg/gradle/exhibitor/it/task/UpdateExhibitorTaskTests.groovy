package com.github.vadeg.gradle.exhibitor.it.task

import com.github.vadeg.gradle.exhibitor.client.ExhibitorClient
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome

class UpdateExhibitorTaskTests
        extends AbstractTaskFunctionalTest {

    def "Configure task using extension"() {
        given:
        buildFile << """
                plugins {
                    id 'com.github.vadeg.exhibitor'
                }
                
                exhibitor {
                    url = 'http://localhost:8080'                                         
                }
            """
        when:
        def result = GradleRunner.create()
                .withPluginClasspath()
                .withDebug(true)
                .withProjectDir(testProjectDir.root)
                .withArguments('updateExhibitor', "--stacktrace")
                .withPluginClasspath(pluginClasspath)
                .build()

        then:
        result.task(":updateExhibitor").outcome == TaskOutcome.SUCCESS
    }

    def "Apply data to exhibitor"() {
        given:
        buildFile << """
                plugins {
                    id 'com.github.vadeg.exhibitor'
                }
                
                exhibitor {
                    url = 'http://localhost:8181'
                    files = ['sample.zkscript']                     
                }
            """
        when:
        def file = testProjectDir.newFile("sample.zkscript")
        file << """
                    create a/b/c value1
                    create a/b/d 123
                    create a this is a test """
        def result = GradleRunner.create()
                .withPluginClasspath()
                .withDebug(true)
                .withProjectDir(testProjectDir.root)
                .withArguments("--debug", 'updateExhibitor')
                .withPluginClasspath(pluginClasspath)
                .build()

        then:
        result.output.contains('sample.zkscript processed')
        result.task(":updateExhibitor").outcome == TaskOutcome.SUCCESS
        def client = new ExhibitorClient("http://localhost:8181")
        def value = client.get("a/b/c")
        value == "value1"

        cleanup:
        client.delete("a")
    }
}
