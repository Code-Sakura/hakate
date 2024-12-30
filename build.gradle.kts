import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    id("root.publication")
    id("module.publication")
    alias(libs.plugins.vanniktech.maven.publish)
    alias(libs.plugins.dokka)
}
val gpId = properties.get("project.groupeId") as String
val ver = properties.get("project.version") as String
group = gpId
version = ver



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



mavenPublishing {
    configure(KotlinMultiplatform(javadocJar = JavadocJar.Dokka("dokkaHtml")))
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    if (project.hasProperty("mavenCentralUsername") ||
        System.getenv("ORG_GRADLE_PROJECT_mavenCentralUsername") != null
    )
        signAllPublications()
}