import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    id("net.kigawa.hakate.java-conventions")
}

//application {
//    mainClass.set("io.ktor.server.netty.EngineMain")
//
//    val isDevelopment: Boolean = project.ext.has("development")
//    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
//}

repositories {
    mavenCentral()
}

dependencies {
}

kotlin {
    js(IR) {
    }
    jvm {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }
    sourceSets["jsMain"].dependencies {
        implementation(libs.kotlin.stdlib.js)
    }
    sourceSets["commonMain"].dependencies {
//        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
//        implementation(libs.kotlinx.coroutines.core)
//        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
    }
    sourceSets["jvmMain"].apply {
        dependencies {
        }
    }
    sourceSets["jvmTest"].apply {
        dependencies {
            implementation(libs.kotlin.test.junit)
//            implementation(kotlin("test"))
        }
    }

//    sourceSets["jsTest"].dependencies {
//    }
}