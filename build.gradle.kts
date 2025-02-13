import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import io.github.patrick.gradle.remapper.RemapTask

plugins {
    id("java")
    id("maven-publish")
    id("com.gradleup.shadow") version "8.3.5"
    id("io.github.patrick.remapper") version "1.4.2"
    id("com.diffplug.spotless") version "7.0.2"
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.14"
}

val majorVersion: String by project
val minorVersion: String by project
val patchVersion: String by project
val baseVersion = "$majorVersion.$minorVersion.$patchVersion"
val isSnapshot = project.property("isSnapshot").toString().toBoolean()
val finalVersion = if (isSnapshot) "$baseVersion-SNAPSHOT" else baseVersion
val buildNumber = System.getenv("BUILD_NUMBER") ?: ""
val isJenkins = buildNumber.isNotEmpty()

val mcVersion: String by project

group = "me.lemonypancakes.${rootProject.name}"
version = if (isJenkins && isSnapshot) "$finalVersion-b$buildNumber" else finalVersion

repositories {
    mavenCentral()
    maven("https://repo.codemc.io/repository/nms/")
    maven("https://repo.codemc.io/repository/lemonypancakes/")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://libraries.minecraft.net/")
}

dependencies {
    paperweight.paperDevBundle("1.21.4-R0.1-SNAPSHOT")
    compileOnly("org.jetbrains:annotations:26.0.2")
    implementation("dev.jorel:commandapi-bukkit-shade:9.7.0")
    implementation("me.lemonypancakes.resourcemanagerhelper:resourcemanagerhelper:1.4.5")
    implementation("org.bstats:bstats-bukkit:3.1.0")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21

    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            version = finalVersion
        }
    }

    repositories {
        maven {
            url = uri("https://repo.codemc.io/repository/lemonypancakes/")

            credentials {
                username = System.getenv("JENKINS_USERNAME")
                password = System.getenv("JENKINS_PASSWORD")
            }
        }
    }
}

spotless {
    java {
        googleJavaFormat()
    }
}

tasks {
    withType<ProcessResources> {
        eachFile {
            expand("version" to finalVersion)
        }
    }

    withType<RemapTask> {
        inputTask.set(jar)
        version.set(mcVersion)
    }

    withType<ShadowJar> {
        dependsOn(withType<RemapTask>())

        archiveClassifier.set("")

        relocate("me.lemonypancakes.resourcemanagerhelper", "me.lemonypancakes.races.libs.resourcemanagerhelper")
        relocate("dev.jorel.commandapi", "me.lemonypancakes.races.libs.commandapi")
        relocate("org.bstats", "me.lemonypancakes.races.metrics")
    }
}
