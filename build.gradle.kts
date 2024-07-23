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
    maven("https://repo.papermc.io/repository/maven-public/")

}

dependencies {
    compileOnly("org.spigotmc:spigot:1.21-R0.1-SNAPSHOT:remapped-mojang")
    compileOnly("dev.folia:folia-api:1.20.6-R0.1-SNAPSHOT")
    implementation("me.lemonypancakes.resourcemanagerhelper:resourcemanagerhelper:1.3.0")
}

spigotRemap {
    spigotVersion.set("1.21")
    sourceJarTask.set(tasks.jar)
}

tasks {
    withType<ShadowJar> {
        archiveClassifier = ""
    }
}

java {
    withSourcesJar()
    withJavadocJar()
}

repositories {
    mavenCentral()
    maven("https://repo.codemc.io/repository/nms/")
    maven("https://libraries.minecraft.net/")
}

tasks {
    javadoc {
        options {
            (this as CoreJavadocOptions).addStringOption("Xdoclint:none", "-quiet")
        }
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
            val releasesRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl

            credentials {
                username = project.findProperty("ossrhUsername")?.toString()
                password = project.findProperty("ossrhPassword")?.toString()
            }
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}
