import java.util.Properties
import java.io.FileInputStream

import kotlin.collections.listOf

plugins {
    alias(libs.plugins.application)
    alias(libs.plugins.google.service)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.detekt)
    alias(libs.plugins.navigation)
}

val keys = Properties().apply {
    load(FileInputStream(File(rootProject.rootDir, "secrets.properties")))
}

android {
    compileSdk = 34
    namespace = "com.bed.seller"

    defaultConfig {
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        applicationId = "com.bed.seller"

        testInstrumentationRunner = "com.bed.seller.CustomTestRunner"
        testInstrumentationRunnerArguments.putAll(
            mapOf(
                "coverage" to "true",
                "disableAnalytics" to "true",
                "clearPackageData" to "true",
                "useTestStorageService" to "false"
            )
        )

        buildConfigField("String", "DATA_STORE", keys.getProperty("DATA_STORE"))
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    testOptions {
        animationsDisabled = true
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            applicationIdSuffix = ".debug"
        }

        register("profile") {
            isMinifyEnabled = true
            isShrinkResources = true
            applicationIdSuffix = ".profile"
            initWith(getByName("debug"))
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules-staging.pro"
            )
        }

        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn")
    }
}

dependencies {
    implementation(project(":core"))

    ksp(libs.hilt.compiler)
    implementation(libs.hilt.android)

    implementation(libs.bundles.others)
    implementation(libs.bundles.androidx)

    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firbase)

    detektPlugins(libs.detekt)

    testImplementation(project(":test"))
    testImplementation(libs.androidx.arch.test)

    androidTestUtil(libs.androidx.orchestrator.test)

    kspAndroidTest(libs.hilt.compiler)

    androidTestImplementation(libs.bundles.androidx.test)

    debugImplementation(libs.bundles.debug)
}

detekt {
    toolVersion = libs.versions.detekt.get()

    parallel = true

    debug = false
    allRules = false
    ignoreFailures = false
    buildUponDefaultConfig = false
    disableDefaultRuleSets = false

    basePath = projectDir.absolutePath
    ignoredBuildTypes = listOf("release")
    config.setFrom(file("$rootDir/config/detekt/detekt.yml"))
    source.setFrom(
        "$rootDir/app/src/main/java",
        "$rootDir/app/src/test/java",
        "$rootDir/app/src/androidTest/java",
        "$rootDir/core/src/main/java",
        "$rootDir/core/src/test/java",
        "$rootDir/test/src/main/java"
    )
}

afterEvaluate {
    tasks.named("preBuild") {
        dependsOn("detekt")
    }
}

tasks.detekt.configure {
    reports {
        sarif.required.set(true)
    }
}
