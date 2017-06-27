package io.vadeg.gradle.exhibitor.task

import groovy.transform.CompileStatic
import org.gradle.api.DefaultTask
import org.gradle.api.provider.PropertyState
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

@CompileStatic
class UpdateExhibitorTask
        extends DefaultTask {

    final PropertyState<String> url = project.property(String)
    final PropertyState<List<String>> files = project.property(List)


    void setUrl(String url) {
        this.url.set(url)
    }

    void setUrl(Provider<String> url) {
        this.url.set(url)
    }

    @Input
    String getUrl() {
        return url.get()
    }

    void setFiles(List<String> files) {
        this.files.set(files)
    }

    void setFiles(Provider<List<String>> files) {
        this.files.set(files)
    }

    @Input
    List<String> getFiles() {
        return files.get()
    }


    @TaskAction
    def process() {
//        println "Url: ${getUrl()} : ${files.getOrNull()}"
        println "Not implemented yet"
    }
}
