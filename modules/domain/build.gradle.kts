plugins {
    kotlin("plugin.jpa")
}

version = "0.0.1-SNAPSHOT"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.hibernate:hibernate-spatial:6.4.4.Final")
    implementation("com.mysql:mysql-connector-j")
}