import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("maven-publish")
    id("signing")
    id("io.typecraft.gradlesource.spigot") version "1.0.0"
}

val majorVersion = project.property("majorVersion") as String
val minorVersion = project.property("minorVersion") as String
val patchVersion = project.property("patchVersion") as String
val isSnapshot = project.property("isSnapshot").toString().toBoolean()
val baseVersion = "$majorVersion.$minorVersion.$patchVersion"
group = "me.lemonypancakes.${rootProject.name}"
version = if (isSnapshot) "$baseVersion-SNAPSHOT" else baseVersion

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21

    withJavadocJar()
    withSourcesJar()
}

repositories {
    mavenCentral()
    maven("https://repo.codemc.io/repository/nms")
    maven("https://libraries.minecraft.net")
    maven("https://repo.papermc.io/repository/maven-public")
    maven("https://repo.codemc.io/repository/lemonypancakes")
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
        }
    }

    repositories {
        maven {
            url = uri("https://repo.codemc.io/repository/lemonypancakes")

            credentials {
                username = System.getenv("JENKINS_USERNAME")
                password = System.getenv("JENKINS_PASSWORD")
            }
        }
    }
}
