import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    kotlin("multiplatform") version "2.1.0"
    `maven-publish`
}
val gpId = "net.kigawa"
val afId = "hakate"
val ver = "1.0.0"
group = gpId
version = ver


publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = gpId
            artifactId = afId
            version = ver

//            from(components["java"])
            pom {
                name = afId
                description = "State management library"
                url = "http://www.example.com/library"
                properties = mapOf(
                    "myProp" to "value",
                    "prop.with.dots" to "anotherValue"
                )
                licenses {
                    license {
                        name = "The Apache License, Version 2.0"
                        url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                }
                developers {
                    developer {
                        id = "johnd"
                        name = "John Doe"
                        email = "john.doe@example.com"
                    }
                }
                scm {
                    connection = "scm:git:git://example.com/my-library.git"
                    developerConnection = "scm:git:ssh://example.com/my-library.git"
                    url = "http://example.com/my-library/"
                }
            }
        }
    }
    publications {
        withType<MavenPublication> {
            artifactId = if (name == "kotlinMultiplatform") artifactId
            else "$artifactId-$name"

            pom {
                name.set("kutil")
                description.set("utilities for kotlin")
                url.set("https://github.com/kigawa01/kutil/")
                properties.set(
                    mapOf(
                    )
                )
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("http://www.opensource.org/licenses/mit-license.php")
                    }
                }
                developers {
                    developer {
                        id.set("net.kigawa")
                        name.set("kigawa")
                        email.set("contact@kigawa.net")
                    }
                }
                scm {
                    connection.set("scm:git:https://github.com/kigawa01/kutil-java.git")
                    developerConnection.set("scm:git:https://github.com/kigawa01/kutil-java.git")
                    url.set("https://github.com/kigawa01/kutil-java")
                }
            }
        }
    }

    repositories {
        maven {
            name = "OSSRH"
//            url = URI("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = System.getenv("MAVEN_USERNAME")
                password = System.getenv("MAVEN_PASSWORD")
            }
        }
    }
}


repositories {
    mavenCentral()
}

dependencies {
}

kotlin {
    jvm {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }
    js {
        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
    }
    sourceSets["commonMain"].dependencies {
        implementation(libs.kotlinx.coroutines.core)
        implementation(libs.kotlinx.datetime)
        implementation(libs.kotlin.stdlib)
    }
    sourceSets["commonTest"].dependencies {
        implementation(kotlin("test-common"))
        implementation(kotlin("test-annotations-common"))
        implementation(libs.kotlinx.coroutines.test)

    }
    sourceSets["jvmMain"].dependencies {
        implementation(libs.kotlin.test.junit)
    }
    sourceSets["jvmTest"].dependencies {

    }

    sourceSets["jsMain"].dependencies {
        implementation(libs.kotlin.stdlib.js)
    }
    sourceSets["jsTest"].dependencies {
        implementation(kotlin("test-js"))
    }
}

