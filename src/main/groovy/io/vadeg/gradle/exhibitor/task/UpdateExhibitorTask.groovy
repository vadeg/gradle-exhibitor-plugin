package io.vadeg.gradle.exhibitor.task

import io.vadeg.gradle.exhibitor.client.CommandLine
import io.vadeg.gradle.exhibitor.client.ExhibitorClient
import org.gradle.api.DefaultTask
import org.gradle.api.provider.PropertyState
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

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
        def client = new ExhibitorClient(validateUrl())
        project.files(getFiles()).each { File file ->
            if (!file.exists()) {
                throw new IllegalArgumentException("File $fileName not found")
            }
            file.readLines().each { String line ->
                def parsedCommand = CommandLine.parse(line)
                if (parsedCommand) {
                    switch (parsedCommand.command) {
                        case CommandLine.Type.CREATE:
                            def result = client.upsert(parsedCommand.path.toString(), parsedCommand.value)
                            logger.debug("Execute: ${parsedCommand.path} -> ${parsedCommand.value} -> ${result.message}")
                            break
                        case CommandLine.Type.DELETE:
                            def result = client.delete(parsedCommand.path.toString())
                            logger.debug("Execute: ${parsedCommand.path} -> ${result.message}")
                            break
                    }
                }
            }
            logger.info("$file.absolutePath processed")
        }
    }

    private def validateUrl() {
        def urlStr = getUrl()
        if (!getUrl()) {
            throw new IllegalArgumentException("url property is not configured")
        }
        new URL(urlStr).toString()
    }
}
