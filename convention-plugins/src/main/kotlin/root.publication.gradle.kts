plugins {
    id("io.github.gradle-nexus.publish-plugin")
}
object Conf {
    const val group = "net.kigawa.hakate"
    const val version = "1.0.0"
}

group = Conf.group
version = Conf.version
allprojects {
    group = Conf.group
    version = Conf.version
}

nexusPublishing {
    // Configure maven central repository
    // https://github.com/gradle-nexus/publish-plugin#publishing-to-maven-central-via-sonatype-ossrh
    repositories {
        sonatype {  //only for users registered in Sonatype after 24 Feb 2021
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
        }
    }
}
