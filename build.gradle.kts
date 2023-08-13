plugins {
    kotlin("jvm") version "1.9.0"
    `maven-publish`
    application

}

group = "me.ritom"
version = "1.0"

repositories {
    mavenCentral()
}
tasks.withType<Jar> {
    exclude("MainKt.class")
}
publishing {
    repositories {
        maven {
            name = "YoutubeDownloader"
            url = uri("https://maven.pkg.github.com/ritomg69/youtube-downloader")
            credentials {
                username = findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
                password = findProperty("gpr.token") as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
}
dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}
