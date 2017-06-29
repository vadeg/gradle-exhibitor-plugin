package com.github.vadeg.gradle.exhibitor

import com.github.vadeg.gradle.exhibitor.domain.ExhibitorExtension
import com.github.vadeg.gradle.exhibitor.task.UpdateExhibitorTask
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class ExhibitorPluginConfigurationTests
        extends Specification {

    def "Test plugin's updateExhibitor task initialization"() {
        given:
        def project = ProjectBuilder.builder().build()
        when:
        project.plugins.apply('com.github.vadeg.exhibitor')
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
        project.plugins.apply('com.github.vadeg.exhibitor')
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
        project.plugins.apply('com.github.vadeg.exhibitor')
        project.extensions.exhibitor.url url
        project.tasks.updateExhibitor.files = files

        then:
        project.tasks.updateExhibitor.url == url
        project.tasks.updateExhibitor.files == files
    }

}
