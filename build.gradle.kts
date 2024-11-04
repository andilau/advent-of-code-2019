import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
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
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
    implementation("org.reflections:reflections:0.10.2")
    implementation("org.slf4j:slf4j-nop:2.0.16")

    testApi("org.junit.jupiter:junit-jupiter-engine:5.11.3")
    testImplementation("org.assertj:assertj-core:3.26.3")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
        kotlinOptions.freeCompilerArgs = listOf("-Xjsr305=strict")
    }
    test {
        useJUnitPlatform()
    }
}
