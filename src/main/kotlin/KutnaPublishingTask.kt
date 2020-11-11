package com.chadmarchand.kutna.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

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
        (project.extensions.findByName("publishing") as PublishingExtension).apply {
            this.publications.apply {
                this.create("mavenPublication", MavenPublication::class.java).apply {
                    from(project.components.getByName("java"))
                    this.groupId = KUTNA_GROUP_ID
                    this.artifactId = publishedArtifactId

                    this.pom.withXml {
                        val root = it.asNode()
                        root.appendNode("description", artifactDescription)
                        root.appendNode("name", publishedArtifactId)
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
            }
        }
    }
}