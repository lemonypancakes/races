import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("com.gradleup.shadow") version "8.3.5"
    id("maven-publish")
    id("io.typecraft.gradlesource.spigot") version "1.0.0"
    id("de.eldoria.plugin-yml.bukkit") version "0.6.0"
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

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21

    withJavadocJar()
    withSourcesJar()
}

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
    implementation("me.lemonypancakes.resourcemanagerhelper:resourcemanagerhelper:1.4.0")
}

bukkit {
    name = "Races"
    description = "Formerly Origins-Bukkit"
    main = "me.lemonypancakes.races.RacesPlugin"
    apiVersion = "1.21"
    foliaSupported = false
}

spigotRemap {
    spigotVersion.set("1.21.4")
    sourceJarTask.set(tasks.jar)
}

tasks {
    withType<ShadowJar> {
        archiveClassifier = ""

        relocate("me.lemonypancakes.resourcemanagerhelper", "me.lemonypancakes.races.libs.resourcemanagerhelper")
        relocate("dev.jorel.commandapi", "me.lemonypancakes.races.libs.commandapi")
    }
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
