import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    kotlin("multiplatform") version "2.1.0"
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
        implementation(libs.kotlin.stdlib)
    }
    sourceSets["commonTest"].dependencies {
        implementation(kotlin("test-common"))
        implementation(kotlin("test-annotations-common"))

    }
    sourceSets["jvmMain"].dependencies {
        implementation(kotlin("test-junit5"))
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

