package com.github.vadeg.gradle.exhibitor

import com.github.vadeg.gradle.exhibitor.domain.ExhibitorExtension
import com.github.vadeg.gradle.exhibitor.task.UpdateExhibitorTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class ExhibitorPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        def extension = project.extensions.create("exhibitor", ExhibitorExtension, project)
        project.tasks.create("updateExhibitor", UpdateExhibitorTask) {
            url = extension.getUrlProvider()
            files = extension.getFilesProvider()
        }
    }
}
