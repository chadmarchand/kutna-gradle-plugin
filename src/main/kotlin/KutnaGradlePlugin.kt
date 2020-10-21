package com.chadmarchand.kutna.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

private fun createSharedModel(group: String, name: String, version: String?): Map<String, String> {
    val sharedModel = hashMapOf(
        Pair("group", group),
        Pair("name", name)
    )

    if (version != null) {
        sharedModel["version"] = version
    }

    return sharedModel
}

private fun addTestImplementationDependency(
    project: Project,
    group: String, name:
    String,
    version: String
) {
    val sharedModel = createSharedModel(group, name, version)
    project.dependencies.add("testImplementation", sharedModel)
}

private fun addTestRuntimeDependency(
    project: Project,
    group: String, name:
    String,
    version: String
) {
    val sharedModel = createSharedModel(group, name, version)
    project.dependencies.add("testRuntimeOnly", sharedModel)
}

private fun addTestDependencies(project: Project) {
    addTestImplementationDependency(project, "org.junit.jupiter", "junit-jupiter-api", "5.4.2")
    addTestRuntimeDependency(project, "org.junit.jupiter", "junit-jupiter-engine", "5.4.2")
}

class KutnaGradlePlugin : Plugin<Project>{
    override fun apply(project: Project) {
        addTestDependencies(project)
    }
}
