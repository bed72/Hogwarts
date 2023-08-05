plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(project(":core"))

    implementation("io.ktor:ktor-client-mock:2.3.3")

    api("junit:junit:4.13.2")
    api("org.mockito.kotlin:mockito-kotlin:5.0.0")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
}
