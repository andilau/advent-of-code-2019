import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "1.7.20"
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
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.2")
    implementation("org.reflections:reflections:0.10.2")
    implementation("org.slf4j:slf4j-nop:1.7.36")

    testApi("org.junit.jupiter:junit-jupiter-engine:5.9.1")
    testImplementation("org.assertj:assertj-core:3.23.1")
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
