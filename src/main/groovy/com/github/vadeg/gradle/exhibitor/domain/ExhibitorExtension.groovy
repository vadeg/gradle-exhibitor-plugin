package com.github.vadeg.gradle.exhibitor.domain

import org.gradle.api.Project
import org.gradle.api.provider.PropertyState
import org.gradle.api.provider.Provider

class ExhibitorExtension {

    final PropertyState<String> url

    final PropertyState<List<String>> files


    ExhibitorExtension(Project project) {
        url = project.property(String)
        files = project.property(List)
        files.set(new ArrayList())
    }

    void setUrl(String url) {
        this.url.set(url)
    }


    String getUrl() {
        return url.get()
    }

    Provider<String> getUrlProvider() {
        url
    }


    void setFiles(List<String> files) {
        this.files.set(files)
    }

    void files(List<String> files) {
        this.setFiles(files)
    }

    List<String> getFiles() {
        this.files.get()
    }

    Provider<List<String>> getFilesProvider() {
        files
    }

}
