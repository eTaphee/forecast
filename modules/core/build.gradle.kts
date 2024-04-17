plugins {}

version = "0.0.1-SNAPSHOT"

tasks.named("bootJar") {
    enabled = false
}

tasks.named("jar") {
    enabled = true
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-validation")
}