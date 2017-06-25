package io.vadeg.gradle.exhibitor

import io.vadeg.gradle.exhibitor.domain.ExhibitorExtension
import io.vadeg.gradle.exhibitor.task.UpdateExhibitorTask
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class ExhibitorPluginConfigurationTests
        extends Specification {

    def "Test plugin's updateExhibitor task initialization"() {
        given:
        def project = ProjectBuilder.builder().build()
        when:
        project.plugins.apply('io.vadeg.gradle.exhibitor')
        then:
        project.tasks.updateExhibitor instanceof UpdateExhibitorTask
        project.extensions.exhibitor instanceof ExhibitorExtension
    }

    def "Configure using extension"() {
        given:
        def project = ProjectBuilder.builder().build()
        def url = "http://localhost:1234"
        def files = ['a', 'b', 'c']

        when:
        project.plugins.apply('io.vadeg.gradle.exhibitor')
        project.extensions.exhibitor.url url
        project.extensions.exhibitor.files files

        then:
        project.tasks.updateExhibitor.url == url
        project.tasks.updateExhibitor.files == files
    }

    def "Configure using extension and task"() {
        given:
        def project = ProjectBuilder.builder().build()
        def url = "http://localhost:1234"
        def files = ['a', 'b', 'c']

        when:
        project.plugins.apply('io.vadeg.gradle.exhibitor')
        project.extensions.exhibitor.url url
        project.tasks.updateExhibitor.files = files

        then:
        project.tasks.updateExhibitor.url == url
        project.tasks.updateExhibitor.files == files
    }

}
