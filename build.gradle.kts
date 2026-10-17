plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.24"
    id("com.diffplug.spotless") version "8.10.4"
    id("com.gradleup.shadow") version "9.6.1"
    id("maven-publish")
}

val majorVersion = project.property("majorVersion").toString()
val minorVersion = project.property("minorVersion").toString()
val patchVersion = project.property("patchVersion").toString()

val baseVersion = "$majorVersion.$minorVersion.$patchVersion"
val isSnapshot = project.property("isSnapshot").toString().toBoolean()

val finalVersion = if (isSnapshot) "$baseVersion-SNAPSHOT" else baseVersion

val buildNumber = System.getenv("BUILD_NUMBER") ?: ""
val isJenkins = buildNumber.isNotEmpty()

val minecraftVersion = project.property("minecraftVersion").toString()

group = "me.lemonypancakes.${rootProject.name}"
version = if (isJenkins && isSnapshot) "$finalVersion-b$buildNumber" else finalVersion

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://libraries.minecraft.net/")
    mavenCentral()
    mavenLocal()
}

dependencies {
    paperweight.paperDevBundle("${minecraftVersion}.build.+")
    compileOnly("org.jetbrains:annotations:26.1.0")
    implementation("dev.jorel:commandapi-paper-shade:12.1.0")
    implementation("org.bstats:bstats-bukkit:3.2.1")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
    withSourcesJar()
    withJavadocJar()
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

    compileJava {
        options.release.set(25)
        options.encoding = "UTF-8"
    }

    shadowJar {
        archiveClassifier.set("")
        relocate("dev.jorel.commandapi", "me.lemonypancakes.races.libraries.commandapi")
        relocate("org.bstats", "me.lemonypancakes.races.metrics")
    }

    build {
        dependsOn(shadowJar)
    }
}
