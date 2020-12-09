package com.chadmarchand.kutna.gradle

import com.jfrog.bintray.gradle.tasks.BintrayUploadTask
import org.gradle.api.Project

private const val BINTRAY_UPLOAD_TASK_NAME = "bintrayUpload"

internal fun Project.getBintrayUploadTask(): BintrayUploadTask =
    (project.tasks.findByName(BINTRAY_UPLOAD_TASK_NAME) as BintrayUploadTask)
