plugins {
    application
    kotlin("jvm") version "2.2.21"
}

application {
    mainClass.set("util.Runner")
}

version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    implementation("org.reflections:reflections:0.10.2")
    implementation("org.slf4j:slf4j-nop:2.0.17")

    testApi("org.junit.jupiter:junit-jupiter-engine:5.13.3")
    testImplementation("org.assertj:assertj-core:3.27.6")
}

kotlin {
    jvmToolchain(17)
    compilerOptions {
        freeCompilerArgs.set(listOf("-Xjsr305=strict"))
    }
    sourceSets.all {
        languageSettings {
            languageVersion = "2.0"
        }
    }
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter()
        }
    }
}
