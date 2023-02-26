rootProject.name = "article"

pluginManagement {
    val kotlinVersion: String by settings
    val kotestVersion: String by settings
    val openapiVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion
        id("io.kotest.multiplatform") version kotestVersion apply false
        id("org.openapi.generator") version openapiVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false
    }
}

include(
    "article-repository",
    "article-web",
    "article-app",
    "article-api",
    "article-api-model-mappers",
    "article-common-model"
)
