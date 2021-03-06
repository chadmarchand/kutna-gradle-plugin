package com.chadmarchand.kutna.gradle

import org.gradle.api.Project
import java.time.Instant
import java.util.*

private const val BINTRAY_USER_ENV_VARIABLE = "BINTRAY_USER"
private const val BINTRAY_KEY_ENV_VARIABLE = "BINTRAY_KEY"
private const val MIT_LICENSE = "MIT"

private fun getRequiredEnvironmentVariable(name: String): String {
    return System.getenv(name) ?: throw SystemVariableAccessException(
        "Environment variable ${name} is not defined"
    )
}

internal fun Project.configureBintrayExtension(
    repo: String,
    projectName: String,
    vcsUrl: String,
    version: String
) {
    getBintrayUploadTask().apply {
        this.repoName = repo
        this.packageName = projectName
        this.versionName = version
        this.user = getRequiredEnvironmentVariable(BINTRAY_USER_ENV_VARIABLE)
        this.apiKey = getRequiredEnvironmentVariable(BINTRAY_KEY_ENV_VARIABLE)
        this.setPublications(MAVEN_PUBLICATION_NAME)
        this.publish = true
        this.versionReleased = Date.from(Instant.now()).toString()
        this.setPackageLicenses(MIT_LICENSE)
        this.packageVcsUrl = vcsUrl
    }
}
