plugins {
    java
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "ads.bcd"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")

    implementation("org.xerial:sqlite-jdbc:3.42.0.0")
    implementation("mysql:mysql-connector-java:8.0.33")
}

application {
    mainClass.set("bcd.Main")
}

tasks.test {
    useJUnitPlatform()
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

tasks.withType<JavaExec>().configureEach {
    standardInput = System.`in`
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "bcd.Principal"
    }
}

tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    mergeServiceFiles()
}
