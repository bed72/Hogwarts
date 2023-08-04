buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.47")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.6.0")
    }
}

plugins {
    id("com.android.application") version "8.1.0" apply false

    id("org.jetbrains.kotlin.jvm") version "1.9.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false

    id("io.gitlab.arturbosch.detekt") version "1.23.1" apply true

    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.0" apply false
}

