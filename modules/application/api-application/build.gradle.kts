plugins {}

version = "0.0.1-SNAPSHOT"

dependencies {
    implementation(project(":modules:core"))
    implementation(project(":modules:domain"))
    implementation(project(":modules:application:core-application"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
}