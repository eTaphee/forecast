plugins {}

version = "0.0.1-SNAPSHOT"

dependencies {
    implementation(project(":modules:core"))
    implementation(project(":modules:external-api"))
    implementation(project(":modules:domain"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.hibernate:hibernate-spatial:6.4.4.Final")
}