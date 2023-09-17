//import java.util.Properties
//import java.io.FileInputStream

import kotlin.collections.listOf

plugins {
    id("kotlin-kapt")
    id("kotlinx-serialization")
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    id("io.gitlab.arturbosch.detekt")
    id("androidx.navigation.safeargs")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

//val keys = Properties().apply {
//    load(FileInputStream(File(rootProject.rootDir, "secrets.properties")))
//}

android {
    compileSdk = 34
    namespace = "com.bed.seller"

    defaultConfig {
        minSdk = 28
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

//        buildConfigField("String", "API_KEY", keys.getProperty("API_KEY"))
//        buildConfigField("String", "BASE_URL", keys.getProperty("BASE_URL"))
    }

    buildFeatures {
        viewBinding = true
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

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    val navigationVersion = "2.7.2"
    implementation("androidx.navigation:navigation-ui-ktx:$navigationVersion")
    implementation("androidx.navigation:navigation-fragment-ktx:$navigationVersion")

    val lifecycleVersion = "2.6.2"
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")

    val hiltVersion = "2.48"
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")
    implementation("com.google.dagger:hilt-android:$hiltVersion")

    implementation(platform("com.google.firebase:firebase-bom:32.2.3"))
    implementation("com.google.firebase:firebase-auth-ktx:22.1.1")
    implementation("com.google.firebase:firebase-analytics-ktx:21.3.0")

    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation("com.airbnb.android:lottie:6.1.0")
//    implementation("io.github.mmolosay:debounce:1.0.0")
    implementation("com.google.android.material:material:1.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.11")

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.1")

    testImplementation(project(":test"))
    testImplementation("androidx.arch.core:core-testing:2.2.0")

    androidTestUtil("androidx.test:orchestrator:1.4.2")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    kaptAndroidTest("com.google.dagger:hilt-android-compiler:$hiltVersion")

    val espressoVersion = "3.5.1"
    androidTestImplementation("androidx.test.espresso:espresso-core:$espressoVersion")
    androidTestImplementation ("androidx.test.espresso:espresso-contrib:$espressoVersion")

    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.navigation:navigation-testing:$navigationVersion")

    androidTestImplementation("com.squareup.okhttp3:mockwebserver:4.9.3")
    androidTestImplementation("com.google.dagger:hilt-android-testing:$hiltVersion")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    debugImplementation("androidx.fragment:fragment-testing:1.6.1")
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.12")
}

detekt {
    toolVersion = "1.23.1"

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
