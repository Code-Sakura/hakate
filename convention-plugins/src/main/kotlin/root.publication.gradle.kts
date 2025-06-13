plugins {
    id("io.github.gradle-nexus.publish-plugin")
}
object Conf {
    const val GROUP = "net.kigawa"
    const val VERSION = "3.3.2"
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
