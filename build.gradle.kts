plugins {
    java
    checkstyle
    id("io.spring.dependency-management") version "1.0.6.RELEASE"
    id("org.springframework.boot") version "2.1.0.RELEASE"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
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
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("com.github.jasminb:jsonapi-converter:0.9")
    implementation("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

checkstyle {
    toolVersion = "8.14"
}
