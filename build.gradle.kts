plugins {
    kotlin("jvm") version "1.9.21"
}

sourceSets {
    main {
        kotlin.srcDir("src")
    }
}

tasks {
    wrapper {
        gradleVersion = "8.5"
    }
}

repositories {
    mavenLocal()
    mavenCentral()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

tasks.test {
    useJUnitPlatform()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter:3.1.2")
    implementation("org.springframework.boot:spring-boot-configuration-processor:3.1.2")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.21")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.21")
    implementation("org.springframework.boot:spring-boot-starter-test:3.1.2")
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("org.hibernate.validator:hibernate-validator:8.0.1.Final")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")
}
