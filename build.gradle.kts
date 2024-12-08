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
    }
    sourceSets["jvmMain"].apply {
        dependencies {
        }
    }
    sourceSets["jvmTest"].apply {
        dependencies {
            implementation(libs.kotlin.test.junit)
            implementation(kotlin("test"))
        }
    }

//    sourceSets["jsTest"].dependencies {
//    }
}