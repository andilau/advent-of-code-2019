import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    application
    `kotlin-dsl`
    kotlin("jvm") version "2.0.21"
}

application {
    mainClass.set("util.Runner")
}

version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")
    implementation("org.reflections:reflections:0.10.2")
    implementation("org.slf4j:slf4j-nop:2.0.17")

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testImplementation("org.assertj:assertj-core:3.27.3")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    jvmToolchain(17)
}

tasks.withType(KotlinCompilationTask::class).configureEach {
    compilerOptions { // Deprecated non-lazy configuration options
        freeCompilerArgs = listOf("--Xjsr305=strict")
    }
}

tasks {
    test {
        useJUnitPlatform()
    }
}
