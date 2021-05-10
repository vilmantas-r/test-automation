/*
 * This file was generated by the Gradle 'init' task.
 */
import java.util.Properties;

plugins {
    `java-library`
    `maven-publish`
    id("com.nortal.test.java-conventions")
    id("io.freefair.lombok")
}

configurations {
    all {
        exclude("org.springframework.boot", "spring-boot-starter-logging")
        exclude("ch.qos.logback", "logback-classic")
    }
}

dependencies {
    api(project(":test-automation-postman"))

    api(libs.bundles.springboot)
    api(libs.bundles.retrofit2)
    api(libs.bundles.cucumber)

    api(libs.guava)
    api(libs.commons.codec)
    api(libs.testcontainers)

    implementation(libs.swagger.request.validator.core)
    implementation(libs.cucumber.reporting)
    implementation(libs.tika.core)
    implementation(libs.org.eclipse.jgit)

    implementation(kotlin("stdlib-jdk8"))

}

description = "test-automation-core"

val props = Properties()
rootProject.file("gradle-local.properties").takeIf { it.exists() }?.inputStream()?.use { props.load(it) }
val nexusUrl: String = System.getenv("GTCT_AMS_NEXUS_URL") ?: props.getProperty("nexusUrl")

repositories {
    mavenCentral()
    maven("https://$nexusUrl/repository/ams-maven/") {
        credentials {
            username = System.getenv("GTCT_AMS_NEXUS_USERNAME") ?: props.getProperty("nexusUsername")
            password = System.getenv("GTCT_AMS_NEXUS_PASSWORD") ?: props.getProperty("nexusPassword")
        }
    }
}

val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.getByName("main").allSource)
}

publishing {
    publications {
        create<MavenPublication>("test-automation-core") {
            from(components["java"])
            artifact(sourcesJar)
        }
    }

    repositories {
        val snapshotsRepoUrl = "https://$nexusUrl/repository/ams-maven-snapshots/"
        val releasesRepoUrl = "https://$nexusUrl/repository/ams-maven-releases/"

        maven(if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl) {
            credentials {
                username = System.getenv("GTCT_AMS_NEXUS_USERNAME") ?: props.getProperty("nexusUsername")
                password = System.getenv("GTCT_AMS_NEXUS_PASSWORD") ?: props.getProperty("nexusPassword")
            }
        }
    }
}
