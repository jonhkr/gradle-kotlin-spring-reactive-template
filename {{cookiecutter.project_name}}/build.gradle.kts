import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("idea")
    id("jacoco")
    id("com.diffplug.spotless") version Versions.spotless
    id("org.springframework.boot") version Versions.springBoot
    id("io.spring.dependency-management") version Versions.dependencyManagement
    kotlin("jvm") version Versions.kotlin
    kotlin("plugin.spring") version Versions.kotlin
}

repositories {
    jcenter()
}

group = "{{cookiecutter.group}}"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

dependencies {
    implementation(Libs.springWebflux)
    implementation(Libs.jacksonKotlin)
    implementation(Libs.reactorKotlinExtensions)
    implementation(Libs.kotlinReflect)
    implementation(Libs.kotlinStdlibJdk8)
    implementation(Libs.kotlinxCoroutinesReactor)
    testImplementation(Libs.springTest) {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation(Libs.reactorTest)
}

tasks.withType<Test> {
    useJUnitPlatform()
}

spotless {
    kotlin {
        ktlint()
    }
    java {
        importOrder()
        removeUnusedImports()
        googleJavaFormat()
        trimTrailingWhitespace()
        endWithNewline()
    }
    kotlinGradle {
        target("*.gradle.kts", "gradle/*.gradle.kts")
        ktlint()
    }
}
