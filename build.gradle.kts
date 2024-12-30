import cl.franciscosolis.sonatypecentralupload.SonatypeCentralUploadTask
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import java.net.URI

plugins {
    kotlin("multiplatform") version "2.1.0"
    `maven-publish`
    alias(libs.plugins.sonatype.central.upload)
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
                url = "https://github.com/kigawa01/hakate"
                properties = mapOf(
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
                    connection.set("scm:git:https://github.com/kigawa01/hakate.git")
                    developerConnection.set("scm:git:https://github.com/kigawa01/hakate.git")
                    url.set("https://github.com/kigawa01/hakate")
                }
            }
        }
        tasks.named<SonatypeCentralUploadTask>("sonatypeCentralUpload") {
            val jarTasks = listOf("jvmJar", "jsJar")
            // 公開するファイルを生成するタスクに依存する。
            dependsOn("sourcesJar", "generatePomFileForMavenPublication", *jarTasks.toTypedArray())

            // Central Portalで生成したトークンを指定する。
            username = System.getenv("MAVEN_USERNAME")
            password = System.getenv("MAVEN_PASSWORD")

            // タスク名から成果物を取得する。
            archives = files(
                *jarTasks.map { tasks.named(it) }.toTypedArray(),
                tasks.named("sourcesJar"),
            )
            // POMファイルをタスクの成果物から取得する。
            pom = file(
                tasks.named("generatePomFileForMavenPublication").get().outputs.files.single()
            )

            // PGPの秘密鍵を指定する。
            signingKey = System.getenv("GPG_PRIVATE_KEY")
            // PGPの秘密鍵のパスフレーズを指定する。
            signingKeyPassphrase = System.getenv("GPG_PASSPHRASE")
        }
    }

    repositories {
        maven {
            name = "OSSRH"
            url = URI("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
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

