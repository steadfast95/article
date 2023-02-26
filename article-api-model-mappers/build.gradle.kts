plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":article-api"))
    implementation(project(":article-common-model"))

    testImplementation(kotlin("test-junit"))
}