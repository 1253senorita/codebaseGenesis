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
    }
}

rootProject.name = "codelab-dataconnect-android"
include(":app") // 앱 모듈 이름이 'app'이 아니라면 실제 이름으로 변경해주세요.