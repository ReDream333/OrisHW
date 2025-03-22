
plugins {
    id("java")
    id("application")
    id("war")
}

group = "ru.kpfu.itis.kononenko"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("jakarta.servlet:jakarta.servlet-api:6.1.0")
    implementation("org.springframework:spring-webmvc:6.2.3")
    implementation("org.springframework:spring-orm:5.3.23")
    implementation("org.springframework.data:spring-data-jpa:3.4.2")

    implementation("org.apache.tomcat.embed:tomcat-embed-jasper:11.0.5")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.2")

    implementation("org.hibernate.orm:hibernate-core:6.5.2.Final")
    implementation("org.postgresql:postgresql:42.7.3")
    implementation("com.zaxxer:HikariCP:4.0.3")

    implementation("org.apache.httpcomponents:httpclient:4.5.13")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.3")

    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
}

tasks.test {
    useJUnitPlatform()
}
