package com.chadmarchand.kutna.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

internal const val MAVEN_PUBLICATION_NAME = "mavenPublication"

open class KutnaPublishingTask : DefaultTask() {
    @Input
    var publishedArtifactId: String = ""

    @Input
    var artifactVersion: String = ""

    @Input
    var artifactDescription: String = ""

    @Input
    var vcsRepoUrl = ""

    @TaskAction
    fun configurePublishing() {
        project.configurePublishingExtension(
            this.publishedArtifactId,
            this.artifactDescription,
            this.vcsRepoUrl,
            this.artifactVersion
        )
        project.configureBintrayExtension(
            this.publishedArtifactId,
            this.publishedArtifactId,
            this.vcsRepoUrl,
            this.artifactVersion
        )
    }
}
