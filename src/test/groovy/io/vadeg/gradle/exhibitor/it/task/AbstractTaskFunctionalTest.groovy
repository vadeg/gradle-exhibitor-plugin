package io.vadeg.gradle.exhibitor.it.task

import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

class AbstractTaskFunctionalTest
        extends Specification {

    @Rule
    final TemporaryFolder testProjectDir = new TemporaryFolder()

    File buildFile
    List<File> pluginClasspath


    def setup() {
        buildFile = testProjectDir.newFile('build.gradle')

        def pluginClasspathResource = getClass().classLoader.findResource("plugin-classpath.txt")
        if (pluginClasspathResource == null) {
            throw new IllegalStateException("Did not find plugin classpath resource, run `testClasses` build task.")
        }

        pluginClasspath = pluginClasspathResource.readLines().collect { new File(it) }
    }

}
