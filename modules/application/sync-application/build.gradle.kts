plugins {}

version = "0.0.1-SNAPSHOT"

tasks.named("bootJar") {
    enabled = true
}

tasks.named("jar") {
    enabled = false
}

dependencies {
    implementation(project(":modules:core"))
    implementation(project(":modules:domain"))
    implementation(project(":modules:external-api"))
    implementation(project(":modules:application:core-application"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    testImplementation("com.ninja-squad:springmockk:4.0.2")
}