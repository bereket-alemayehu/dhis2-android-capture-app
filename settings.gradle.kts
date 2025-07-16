include(
    ":app",
    ":dhis_android_analytics", ":form", ":commons",
    ":dhis2_android_maps", ":compose-table", ":ui-components",
    ":stock-usecase"
)
include(":dhis2-mobile-program-rules")
include(":tracker")
include(":aggregates")
include(":commonskmm")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven { url = uri("https://central.sonatype.com/repository/maven-snapshots") }
        maven { url = uri("https://jitpack.io") }
        mavenLocal()
        maven {
            url = uri("https://maven.google.com")
        }
    }
}


