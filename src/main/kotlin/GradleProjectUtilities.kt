package com.chadmarchand.kutna.gradle

import org.gradle.api.Project

internal fun Project.addPlugin(
    path: String,
    version: String?
) {
    this.plugins.apply(path)
}

internal fun Project.addTestImplementationDependency(
    group: String,
    name: String,
    version: String?
) {
    this.addDependency(
        "testImplementation",
        group,
        name,
        version
    )
}

internal fun Project.addTestRuntimeDependency(
    group: String,
    name: String,
    version: String?
) {
    this.addDependency(
        "testRuntimeOnly",
        group,
        name,
        version
    )
}

internal fun Project.addImplementationDependency(
    group: String,
    name: String,
    version: String?
) {
    this.addDependency(
        "implementation",
        group,
        name,
        version
    )
}

internal fun Project.addDependency(
    dependencyType: String,
    group: String,
    name: String,
    version: String?
) {
    this.dependencies.add(
        dependencyType,
        createDependencyModel(group, name, version)
    )
}

private fun createDependencyModel(
    group: String,
    name: String,
    version: String?
): Map<String, String> {
    val sharedModel = hashMapOf(
        Pair("group", group),
        Pair("name", name)
    )

    if (version != null) {
        sharedModel["version"] = version
    }

    return sharedModel
}
