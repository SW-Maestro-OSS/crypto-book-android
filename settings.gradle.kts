pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "CryptoBook"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":coin-list:domain")
include(":coin-list:data")
include(":coin-list:presentation")
include(":coin-detail:presentation")
include(":settings:presentation")
include(":core:presentation")
