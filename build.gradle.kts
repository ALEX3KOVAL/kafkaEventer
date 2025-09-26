plugins {
    id("java")
    id("org.springframework.boot") version "3.5.4"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "ru.alex3koval"
version = "0.0.1-SNAPSHOT"

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

tasks {
    val copyDeps = register("copyDeps", Copy::class) {
        from(configurations.runtimeClasspath.get())
        into(rootProject.layout.buildDirectory.dir("libs"))
        outputs.upToDateWhen { true }
    }

    val copyResources = register("copyResources", Copy::class) {
        from(sourceSets.main.get().resources)
        into(rootProject.layout.buildDirectory.dir("resources").get().asFile.absolutePath)

        outputs.upToDateWhen { true }
    }

    /*bootJar {
        destinationDirectory.set(rootProject.layout.buildDirectory.get())
        archiveFileName.set("eventerApp.jar")

        manifest {
            attributes.apply {
                put("Class-Path", configurations.runtimeClasspath.get()
                    .filter { it.extension == "jar" }
                    .distinctBy { it.name }
                    .joinToString(separator = " ", transform = { "libs/${it.name}" }))
            }
        }

        dependsOn(copyDeps)
        dependsOn(copyResources)
    }*/
}

tasks.test {
    useJUnitPlatform()
}
