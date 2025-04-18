plugins {
    id("root.publication")
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.dokka).apply(false)
    alias(libs.plugins.vanniktech.maven.publish).apply(false)
}
