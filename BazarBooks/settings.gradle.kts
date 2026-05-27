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

rootProject.name = "BazarBooks"

include(":app")
include(":core:common")
include(":core:domain")
include(":core:data")
include(":core:designsystem")
include(":core:navigation")
include(":feature:onboarding")
include(":feature:auth")
include(":feature:home")
include(":feature:profile")
include(":feature:notifications")
include(":feature:orders")
include(":feature:cart_checkout")
