buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.48")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.1")
    }
}

plugins {
    id("com.android.application") version "8.1.1" apply false

    id("io.gitlab.arturbosch.detekt") version "1.23.1" apply true

    id("com.google.gms.google-services") version "4.3.15" apply false

    id("org.jetbrains.kotlin.jvm") version "1.9.10" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.10" apply false
}

tasks.register("copyGitHooks", Copy::class.java) {
    group = "git hooks"
    into("$rootDir/.git/hooks/")
    from("$rootDir/scripts/pre-commit")
    description = "Copies the git hooks from /git-hooks to the .git folder."
}

tasks.register("installGitHooks", Exec::class.java) {
    group = "git hooks"
    workingDir = rootDir
    commandLine = listOf("chmod")
    args("-R", "+x", ".git/hooks/")
    dependsOn("copyGitHooks")
    description = "Installs the pre-commit git hooks from /git-hooks."
    doLast {
        logger.info("Git hook installed successfully.")
    }
}

afterEvaluate {
    tasks.getByPath(":app:preBuild").dependsOn(":installGitHooks")
}