/**
 * Root publication configuration script for the project.
 * This script sets up the necessary configuration for publishing artifacts to Maven Central.
 */
plugins {
    id("io.github.gradle-nexus.publish-plugin")
}

/**
 * Configuration object containing project-wide constants.
 */
object Conf {
    /**
     * The group ID used for all artifacts in this project.
     */
    const val GROUP = "net.kigawa"

    /**
     * The version number for all artifacts in this project.
     */
    const val VERSION = "3.4.0"
}

group = Conf.GROUP
version = Conf.VERSION
allprojects {
    group = Conf.GROUP
    version = Conf.VERSION
}

nexusPublishing {
    // Configure maven central repository
    // https://github.com/gradle-nexus/publish-plugin#publishing-to-maven-central-via-sonatype-ossrh
    repositories {
        sonatype()
    }
}
