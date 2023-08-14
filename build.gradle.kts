plugins {
    kotlin("jvm") version "1.9.0"
    `maven-publish`
    application

}

repositories {
    mavenCentral()
}
tasks.withType<Jar> {
    exclude("MainKt.class")
}
publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            groupId = "site.ritom"
            artifactId = "youtubedownloader"
            version = "1.0" // Replace with the actual version

            pom {
                name = "site.ritom.youtubedownloader"
                description = "Download youtube videos with ease"
                licenses {
                    license {
                        name = "MIT License"
                        url = "https://raw.githubusercontent.com/lukflug/PanelStudio/main/LICENSE"
                    }
                }
                developers {
                    developer {
                        id = "ritomg69"
                        name = "Ritom"
                        email = "ritomghosh01@gmail.com"
                    }
                }
            }
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/RitomG69/youtube-downloader")
            credentials {
                username = "RitomG69"
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
