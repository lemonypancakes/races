plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.18"
    id("com.diffplug.spotless") version "7.0.2"
    id("com.gradleup.shadow") version "8.3.6"
    id("maven-publish")
}

val majorVersion: String by project
val minorVersion: String by project
val patchVersion: String by project
val baseVersion = "$majorVersion.$minorVersion.$patchVersion"
val isSnapshot = project.property("isSnapshot").toString().toBoolean()
val finalVersion = if (isSnapshot) "$baseVersion-SNAPSHOT" else baseVersion
val buildNumber = System.getenv("BUILD_NUMBER") ?: ""
val isJenkins = buildNumber.isNotEmpty()

val minecraftVersion: String by project

group = "me.lemonypancakes.${rootProject.name}"
version = if (isJenkins && isSnapshot) "$finalVersion-b$buildNumber" else finalVersion

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://libraries.minecraft.net/")
    mavenCentral()
    mavenLocal()
}

dependencies {
    paperweight.paperDevBundle("${minecraftVersion}-R0.1-SNAPSHOT")
    compileOnly("org.jetbrains:annotations:26.0.2")
    implementation("dev.jorel:commandapi-bukkit-shade-mojang-mapped:10.1.1")
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
    assemble {
        dependsOn(clean)
    }

    processResources {
        eachFile {
            expand("version" to version)
        }
    }

    shadowJar {
        archiveClassifier.set("")
        relocate("dev.jorel.commandapi", "me.lemonypancakes.races.libs.commandapi")
        relocate("org.bstats", "me.lemonypancakes.races.metrics")
    }

    build {
        dependsOn(shadowJar)
    }
}
