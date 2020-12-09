package com.chadmarchand.kutna.gradle

import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication

private const val KUTNA_GROUP_ID = "com.chadmarchand.kutna"
private const val PUBLISHING_EXTENSION_NAME = "publishing"

internal fun Project.configurePublishingExtension(
    artifactId: String,
    artifactDescription: String,
    vcsRepoUrl: String,
    artifactVersion: String
) {
    getPublishingExtension().apply {
        this.publications.apply {
            this.create(MAVEN_PUBLICATION_NAME, MavenPublication::class.java).apply {
                this.initializeMavenPublication(
                    project,
                    artifactId,
                    artifactDescription,
                    vcsRepoUrl,
                    artifactVersion
                )
            }
        }
    }
}

private fun Project.getPublishingExtension(): PublishingExtension =
    (project.extensions.findByName(PUBLISHING_EXTENSION_NAME) as PublishingExtension)

private fun MavenPublication.initializeMavenPublication(
    project: Project,
    artifactId: String,
    artifactDescription: String,
    vcsRepoUrl: String,
    artifactVersion: String
) {
    from(project.components.getByName("java"))
    this.groupId = KUTNA_GROUP_ID
    this.artifactId = artifactId

    this.pom.withXml {
        val root = it.asNode()
        root.appendNode("description", artifactDescription)
        root.appendNode("name", artifactId)
        root.appendNode("url", vcsRepoUrl)

        val licensesNode = root.appendNode("licenses")
        val licenseNode = licensesNode.appendNode("license")
        licenseNode.appendNode("name", "MIT License")
        licenseNode.appendNode("url", "http://www.opensource.org/licenses/mit-license")
        licenseNode.appendNode("distribution", "repo")

        val developersNode = root.appendNode("developers")
        val developerNode = developersNode.appendNode("developer")
        developerNode.appendNode("id", "chadmarchand")
        developerNode.appendNode("name", "Chad Marchand")

        val scmNode = root.appendNode("scm")
        scmNode.appendNode("url", vcsRepoUrl)
    }

    this.artifact(project.tasks.getByName("testJar")).classifier = "test"

    this.version = artifactVersion
    this.pom.packaging = "jar"
}
