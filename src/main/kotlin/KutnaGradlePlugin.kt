package com.chadmarchand.kutna.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class KutnaGradlePlugin : Plugin<Project>{
    override fun apply(project: Project) {
        addPlugins(project)

        addKotlinDependencies(project)
        addJUnitDependencies(project)
        addKoinDependencies(project)
    }

    private fun addPlugins(project: Project) {
        project.addPlugin("org.jetbrains.kotlin.jvm", "1.3.72")
    }

    private fun addKotlinDependencies(project: Project) {
        project.addImplementationDependency("org.jetbrains.kotlin", "kotlin-stdlib-jdk8", null)
    }

    private fun addKoinDependencies(project: Project) {
        val koinGroup = "org.koin"
        val koinVersion = "2.1.6"
        val koinJavaVersion = "2.1.0-alpha-2"

        project.addImplementationDependency(koinGroup, "koin-core", koinVersion)
        project.addImplementationDependency(koinGroup, "koin-core-ext", koinVersion)
        project.addImplementationDependency(koinGroup, "koin-java", koinJavaVersion)
        project.addTestImplementationDependency(koinGroup, "koin-test", koinVersion)
    }

    private fun addJUnitDependencies(project: Project) {
        val junitGroup = "org.junit.jupiter"
        val junitVersion = "5.4.2"

        project.addTestImplementationDependency(junitGroup, "junit-jupiter-api", junitVersion)
        project.addTestRuntimeDependency(junitGroup, "junit-jupiter-engine", junitVersion)
    }
}
