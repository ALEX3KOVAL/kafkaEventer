plugins {
    id("java-library")
    id("org.springframework.boot") version "3.5.4"
    id("io.spring.dependency-management") version "1.1.7"
    id("maven-publish")
}

group = "ru.alex3koval"
version = "1.0.1"

repositories {
    mavenCentral()
    loadEventingGithubPackages()
}

dependencies {
    implementation("alex3koval:eventing-contract:1.13.+")
    implementation("alex3koval:eventing-impl:1.0.+")

    implementation("org.springframework:spring-context:6.1.5")
    implementation("org.springframework.boot:spring-boot-configuration-processor:3.5.6")
    implementation("org.springframework.boot:spring-boot-autoconfigure:3.5.4")

    compileOnly("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")

    implementation("org.springframework.cloud:spring-cloud-stream-binder-kafka:4.3.0")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    //implementation("org.springframework.boot:spring-boot-starter-data-rest")
    //implementation("org.springframework.boot:spring-boot-starter-hateoas")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    //implementation("org.springframework.boot:spring-boot-starter-webflux")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])

            groupId = "alex3koval"
            artifactId = "kafka-eventer"
            version = "1.0.1"
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/ALEX3KOVAL/kafkaEventer")

            credentials {
                username = "ALEX3KOVAL"
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}
