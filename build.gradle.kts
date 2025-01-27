import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("com.gradleup.shadow") version "8.3.5"
    id("maven-publish")
    id("io.github.patrick.remapper") version "1.4.2"
}

val majorVersion = project.property("majorVersion") as String
val minorVersion = project.property("minorVersion") as String
val patchVersion = project.property("patchVersion") as String
val baseVersion = "$majorVersion.$minorVersion.$patchVersion"
val isSnapshot = project.property("isSnapshot").toString().toBoolean()
val finalVersion = if (isSnapshot) "$baseVersion-SNAPSHOT" else baseVersion
val buildNumber = System.getenv("BUILD_NUMBER") ?: ""
val isJenkins = buildNumber.isNotEmpty()

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
    compileOnly("org.spigotmc:spigot:1.21.4-R0.1-SNAPSHOT:remapped-mojang")
    compileOnly("dev.folia:folia-api:1.20.6-R0.1-SNAPSHOT")
    implementation("dev.jorel:commandapi-bukkit-shade:9.7.0")
    implementation("me.lemonypancakes.resourcemanagerhelper:resourcemanagerhelper:1.4.3")
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

tasks {
    withType<ShadowJar> {
        archiveClassifier.set("")

        relocate("me.lemonypancakes.resourcemanagerhelper", "me.lemonypancakes.races.libs.resourcemanagerhelper")
        relocate("dev.jorel.commandapi", "me.lemonypancakes.races.libs.commandapi")
    }
}
