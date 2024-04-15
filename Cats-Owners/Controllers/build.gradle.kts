plugins {
    id("java-library")
    id("org.springframework.boot") version "3.2.4" apply false
}

apply(plugin = "io.spring.dependency-management")
group = "org.igorM"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(mapOf("path" to ":Cats-Owners:Service")))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.projectlombok:lombok:1.18.26")
    //implementation("org.hibernate:hibernate-core:5.6.3.Final")
    //implementation("org.postgresql:postgresql:42.7.0")
    testImplementation("com.h2database:h2:2.2.222")
    implementation("org.springframework.boot:spring-boot-starter-web:3.2.4")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.2.4")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}