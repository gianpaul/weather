// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://plugins.gradle.org/m2/") }
    }

    dependencies {
        classpath(Classpath.Dependencies.gradle)
        classpath(Classpath.Dependencies.kotlin)
        classpath(Classpath.Dependencies.gradlePlugin)
        classpath(Classpath.Dependencies.hilt)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")
    }

    GradleProperties.init(rootDir)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}