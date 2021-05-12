plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.4.31"

    // Apply the java-library plugin for API and implementation separation.
    `java-library`
    `maven-publish`
    signing
}

group = "nl.asrr"
version = "0.1.2"

java.sourceCompatibility = JavaVersion.VERSION_11

java {
    withJavadocJar()
    withSourcesJar()
}


publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/ASRRtechnologies/lib-asrr-common-kt")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            pom {
                name.set("ASRR Common Kotlin Libary")
                description.set("A library for all common ASRR code")
                url.set("https://www.asrr.nl")
                properties.set(
                    mapOf(
                        "version" to version
                    )
                )
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("Amar97")
                        name.set("Amar Ramdas")
                        email.set("amar.ramdas@asrr.nl")
                    }
                }
            }


            artifactId = "common"

            from(components["java"])
        }
    }
}


repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

java {
    withSourcesJar()
}

dependencies {
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // This dependency is used internally, and not exposed to consumers on their own compile classpath.
    implementation("com.google.guava:guava:30.0-jre")

    // Use the Kotlin test library.
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // Use the Kotlin JUnit integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")

    // This dependency is exported to consumers, that is to say found on their compile classpath.
    api("org.apache.commons:commons-math3:3.6.1")
}

tasks.jar {
    manifest {
        attributes(mapOf("Implementation-Title" to project.name,
            "Implementation-Version" to project.version))
    }
}


