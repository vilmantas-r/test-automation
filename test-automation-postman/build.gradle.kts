configurations {
    all {
        exclude("org.springframework.boot", "spring-boot-starter-logging")
        exclude("ch.qos.logback", "logback-classic")
    }
}
dependencies {
    implementation("org.apache.commons:commons-lang3:3.9")
    implementation("com.google.guava:guava:28.1-jre")
    implementation("com.squareup.retrofit2:retrofit:2.7.1")
//    implementation("org.slf4j:slf4j-api:1.7.29")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.10.1")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.10.1")
    implementation("org.springframework.boot:spring-boot-starter:2.4.5")
    implementation("org.springframework.boot:spring-boot-starter-log4j2:2.4.5")
    implementation("commons-io:commons-io:2.5")
    implementation("org.mapdb:mapdb:3.0.8")
    testImplementation("junit:junit:4.13.2")
}