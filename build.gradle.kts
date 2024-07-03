plugins {
    id("java")
}

group = "me.lemonypancakes.races"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

}

tasks.test {
    useJUnitPlatform()
}
