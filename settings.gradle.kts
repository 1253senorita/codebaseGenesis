pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://www.jitpack.io") }

    }
}

rootProject.name = "codelab-dataconnect-android"

include(":app") // 각 include 문을 별도의 줄로 분리

include(":tytpdfmodule") // 각 include 문을 별도의 줄로 분리