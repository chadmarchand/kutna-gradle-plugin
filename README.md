# kutna-gradle-plugin

This gradle plugin is intended for use with the Kutna framework.
When applied, the plugin adds various gradle dependencies that are common to many Kutna projects.
The plugin also handles publishing and uploading artifacts to Bintray.

## Using the plugin

Apply the plugin in build.gradle, replacing the placeholders in the configurePublishing task with your project-specific details:

```
buildscript {
  repositories {
    maven {
            url 'https://dl.bintray.com/cmarchand/kutna-gradle-plugin'
        }
  }
  
  dependencies {
    classpath 'com.chadmarchand.kutna:kutna-gradle-plugin:1.0.+'
  }
}

apply plugin: com.chadmarchand.kutna.gradle.KutnaPlugin

task configurePublishing(type: com.chadmarchand.kutna.gradle.KutnaPublishingTask) {
    publishedArtifactId = "<your-artifact-id>"
    artifactVersion = "<your-artifact-version>"
    artifactDescription = "<your-artifact-description>"
    vcsRepoUrl = "<your-artifact-vcs-repo>"
}
```

## Using the bintrayUpload task

When the plugin is applied, you can run the _bintrayUpload_ gradle task and the artifacts will be published uploaded to bintray.
The BINTRAY_USER and BINTRAY_KEY environment variables are used for authentication.

```
./gradlew bintrayUpload
```

