
plugins {
    id("java")
    id("application")
    id("war")
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "ru.kpfu.itis.kononenko"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClass = "ru.kpfu.itis.kononenko.Main"
}

val springVersion: String by project
val jakartaVersion: String by project
val hibernateVersion: String by project
val postgresVersion: String by project
val springSecurityVersion: String by project

dependencies {
    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-security")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.boot:spring-boot-starter-freemarker")
        implementation("org.springframework.boot:spring-boot-starter-mail")
        implementation("org.springframework.boot:spring-boot-starter-validation")
        implementation("org.springframework.security:spring-security-taglibs:$springSecurityVersion")
        implementation("jakarta.servlet:jakarta.servlet-api:$jakartaVersion")
        implementation("org.postgresql:postgresql:$postgresVersion")
        implementation("com.mchange:c3p0:0.10.2")
        implementation("javax.mail:javax.mail-api:1.6.2")
        implementation("com.fasterxml.jackson.core:jackson-databind:2.18.3")
        implementation("org.apache.tomcat:tomcat-jsp-api:10.1.20")
        implementation("javax.servlet.jsp:jsp-api:2.1")

    }
}

tasks.test {
    useJUnitPlatform()
}
