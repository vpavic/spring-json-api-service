import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {
    java
    checkstyle
    id("org.springframework.boot") version "2.1.7.RELEASE"
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
}

repositories {
    mavenCentral()
}

configurations {
    all {
        exclude("com.squareup.retrofit2")
    }
}

dependencies {
    implementation(platform(SpringBootPlugin.BOM_COORDINATES))
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("com.github.jasminb:jsonapi-converter:0.9")
    implementation("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

checkstyle {
    toolVersion = "8.14"
}
