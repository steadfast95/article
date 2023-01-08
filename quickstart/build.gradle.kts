plugins {
    kotlin("jvm")
}

group = "ru.otuskotlin.public.article"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib:1.7.10"))
    testImplementation(kotlin("test-junit:1.7.10"))
}