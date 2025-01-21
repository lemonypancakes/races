import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("maven-publish")
    id("signing")
    id("io.typecraft.gradlesource.spigot") version "1.0.0"
}

group = "me.lemonypancakes.races"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.codemc.io/repository/nms")
    maven("https://libraries.minecraft.net/")
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("org.spigotmc:spigot:1.21.4-R0.1-SNAPSHOT:remapped-mojang")
    compileOnly("dev.folia:folia-api:1.20.6-R0.1-SNAPSHOT")
    implementation("dev.jorel:commandapi-bukkit-shade:9.7.0")
    implementation("me.lemonypancakes.resourcemanagerhelper:resourcemanagerhelper:1.4.0")
}

spigotRemap {
    spigotVersion.set("1.21.4")
    sourceJarTask.set(tasks.jar)
}

tasks {
    withType<ShadowJar> {
        archiveClassifier = ""
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            pom {
                groupId = "me.lemonypancakes.races"
                name = "Races"
                description = "Formerly Origins-Bukkit"
                url = "https://github.com/lemonypancakes/races"
                inceptionYear = "2024"
                packaging = "jar"

                licenses {
                    license {
                        name = "GNU General Public License, Version 3.0"
                        url = "https://www.gnu.org/licenses/gpl-3.0.txt"
                        distribution = "repo"
                        comments = "A copyleft license that ensures software freedom"
                    }
                }

                scm {
                    url = "https://github.com/lemonypancakes/races"
                    connection = "scm:git://github.com:lemonypancakes/races.git"
                    developerConnection = "scm:git://github.com:lemonypancakes/races.git"
                }

                developers {
                    developer {
                        id = "lemonypancakes"
                        name = "Teofilo Jr. Daquipil"
                        url = "https://lemonypancakes.me"
                        email = "jiboyjune@gmail.com"
                        roles = listOf("developer", "maintainer")
                    }
                }
            }
        }
    }

    repositories {
        maven {
            credentials {
                username = System.getenv("JENKINS_USERNAME")
                password = System.getenv("JENKINS_PASSWORD")
            }

            isAllowInsecureProtocol = true

            url = uri(project.findProperty("repositoryURL") ?: "https://repo.codemc.io/repository/lemonypancakes/")
        }
    }
}
