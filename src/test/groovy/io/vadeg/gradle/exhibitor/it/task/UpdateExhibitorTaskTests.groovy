package io.vadeg.gradle.exhibitor.it.task

import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome

class UpdateExhibitorTaskTests
        extends AbstractTaskFunctionalTest {

    def "Configure task using extension"() {
        given:
        buildFile << """
                plugins {
                    id 'io.vadeg.gradle.exhibitor'
                }
                
                exhibitor {
                    url = 'http://localhost:8080'
                    files = ['a', 'b']                     
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
        result.output.contains('Url: http://localhost:8080 : [a, b]')
        result.task(":updateExhibitor").outcome == TaskOutcome.SUCCESS
    }
}
