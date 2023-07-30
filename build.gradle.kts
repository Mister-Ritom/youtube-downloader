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
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()
        }
    }

    repositories {
        mavenCentral()
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
