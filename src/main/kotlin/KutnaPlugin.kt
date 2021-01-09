package com.chadmarchand.kutna.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.tasks.testing.Test
import org.gradle.jvm.tasks.Jar

class KutnaPlugin : Plugin<Project>{
    override fun apply(project: Project) {
        project.apply {
            addPlugins(project)

            addKotlinDependencies(project)
            addJUnitDependencies(project)
            addAssertionDependencies(project)
            addKoinDependencies(project)
            addSerializationDependencies(project)
            addLoggingDependencies(project)

            configureTestTask(project)
            configurePublishTask(project)
            configureBintrayUploadTask(project)
        }
    }

    private fun configureTestTask(project: Project) {
        val testTask = (project.tasks.getByName("test") as Test)

        testTask.useJUnitPlatform()
        testTask.environment("USE_TEST_DB", true)
    }

    private fun addPlugins(project: Project) {
        project.plugins.apply("org.jetbrains.kotlin.jvm")
        project.plugins.apply("maven-publish")
        project.plugins.apply("com.jfrog.bintray")
    }

    private fun addKotlinDependencies(project: Project) {
        project.addImplementationDependency("org.jetbrains.kotlin", "kotlin-stdlib-jdk8", null)
    }

    private fun addKoinDependencies(project: Project) {
        val koinGroup = "org.koin"
        val koinVersion = "2.2.0"

        project.addImplementationDependency(koinGroup, "koin-core", koinVersion)
        project.addImplementationDependency(koinGroup, "koin-core-ext", koinVersion)
        project.addTestImplementationDependency(koinGroup, "koin-test", koinVersion)
    }

    private fun addJUnitDependencies(project: Project) {
        val junitGroup = "org.junit.jupiter"
        val junitVersion = "5.4.2"

        project.addTestImplementationDependency(junitGroup, "junit-jupiter-api", junitVersion)
        project.addTestRuntimeDependency(junitGroup, "junit-jupiter-engine", junitVersion)
    }

    private fun addAssertionDependencies(project: Project) {
        project.addTestImplementationDependency("org.assertj", "assertj-core", "3.9.1")
    }

    private fun addLoggingDependencies(project: Project) {
        project.addImplementationDependency("io.github.microutils", "kotlin-logging", "1.7.7")
    }

    private fun addSerializationDependencies(project: Project) {
        project.addTestImplementationDependency("org.json", "json", "20180813")
    }

    private fun configurePublishTask(project: Project) {
        configureTestArtifacts(project)
        configureSourcesJarTask(project)
        configureTestJarTask(project)
    }

    private fun configureTestArtifacts(project: Project) {
        val testCompileConfiguration = (project.configurations.getByName("testCompile"))
        val testArtifactsConfiguration = project.configurations.create("testArtifacts")
        testArtifactsConfiguration.extendsFrom(testCompileConfiguration)
    }

    private fun configureSourcesJarTask(project: Project) {
        project.tasks.getByName("jar") {
            (it as Jar).from(
                project.convention.getPlugin(
                    JavaPluginConvention::class.java
                ).sourceSets.getByName("main").allSource
            )
        }

        project.artifacts.add("archives", project.tasks.getByName("jar"))
    }

    private fun configureTestJarTask(project: Project) {
        project.tasks.register("testJar", Jar::class.java) {
            (it as Jar).from(
                project.convention.getPlugin(
                    JavaPluginConvention::class.java
                ).sourceSets.getByName("test").output
            )
            it.classifier = "test"
        }

        project.artifacts.add("testArtifacts", project.tasks.getByName("testJar"))
    }

    private fun configureBintrayUploadTask(project: Project) {
        project.getBintrayUploadTask().setDependsOn(mutableSetOf("configurePublishing", "jar", "testJar"))
    }
}
