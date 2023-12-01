plugins {
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.detekt) apply true
    alias(libs.plugins.navigation) apply false

    alias(libs.plugins.application) apply false

    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.serialization) apply false
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

true