plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    api("javax.inject:javax.inject:1")
    api("io.arrow-kt:arrow-core:1.2.1")

//    api("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    testImplementation(project(":test"))
}
