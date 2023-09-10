extra["kotlin-coroutines.version"] = "1.6.0"

plugins {
    kotlin("jvm")
    id("com.google.cloud.tools.jib")
    id("org.jetbrains.kotlin.kapt")
//    id("io.gitlab.arturbosch.detekt")
    id("org.jetbrains.kotlin.plugin.jpa")
    id("org.springframework.boot")
    id("org.jetbrains.kotlin.plugin.spring")
    id("org.jetbrains.kotlin.plugin.noarg")
    id("org.jetbrains.kotlin.plugin.allopen")
    id("io.spring.dependency-management")
}

tasks {
    bootJar {
        enabled = false
    }

    jar {
        enabled = true
    }

    compileJava {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }

    compileKotlin {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    compileTestKotlin {
        kotlinOptions.jvmTarget = "11"
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
}

allprojects {
    group = "com.biuea.sportsplatform"
    version = "1.0.0"

    repositories {
        mavenCentral()
        mavenLocal()
        maven {
            url = uri("https://maven.pkg.github.com/biuea3866/sports-platform-file-server")
            credentials {
                username = System.getenv("GITHUB_USERNAME")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "com.google.cloud.tools.jib")
    apply(plugin = "org.jetbrains.kotlin.kapt")
//    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "org.jetbrains.kotlin.plugin.jpa")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "io.spring.dependency-management")

    tasks {
        withType<Test> {
            useJUnitPlatform()
            maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2)
        }

        getByName("test") {
            enabled = false
        }

        register<Test>("unitTest") {
            group = "verification"
            exclude("**/*IntegrationTest.*")
//
//            extensions.configure(JacocoTaskExtension::class.java) {
//                this.setDestinationFile(file("$buildDir/jacoco/test.exec"))
//            }
//
//            finalizedBy("jacocoTestReport")
        }

        register<Test>("integrationTest") {
            group = "verification"
            include("**/*IntegrationTest.*")
        }

        register("prepareKotlinBuildScriptModel") 

        compileKotlin {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = "11"
            }
        }

        compileTestKotlin {
            kotlinOptions.jvmTarget = "11"
        }
    }

    noArg {
        annotation("javax.persistence.Entity")
    }

    allOpen {
        annotations(
            "javax.persistence.Entity",
            "javax.persistence.MappedSuperclass",
            "javax.persistence.Embeddable"
        )
    }

    dependencies {
        val datadogAgentVersion: String by project
        val datadogTraceApiVersion: String by project
        val kotlinVersion: String by project
        val coroutinesVersion: String by project
        val springBootVersion: String by project
        val resilience4jVersion: String by project
        val arrowVersion: String by project
        val commonsPoolVersion: String by project
        val kotlinFakerVersion: String by project
        val mockitoKotlinVersion: String by project
        val kluentVersion: String by project
        val mockkVersion: String by project
        val junitJupiterVersion: String by project
        val awsKotlinSdkVersion: String by project
        val awsCrtVersion: String by project
        val logbackContribVersion: String by project
        val guavaVersion: String by project
        val retrofitVersion: String by project
        val okHttpVersion: String by project
        val jacksonVersion: String by project
        val mockitoVersion: String by project
        val gsonVersion: String by project
        val jjwtVersion: String by project
        val logbackVersion: String by project
        val slf4jVersion: String by project
        val totpVersion: String by project
        val emojiJavaVersion: String by project
        val mapstructVersion: String by project
        val sentryVersion: String by project
        val springDocVersion: String by project
        val oktaVersion: String by project
        val apachePoiVersion: String by project
        val hypersistenceUtilsVesion: String by project

        // Kotlin Dependencies
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        // Spring Boot Depedenencies
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-webflux")
        implementation("org.springframework.boot:spring-boot-starter-validation")
        implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
        implementation("org.springframework.boot:spring-boot-starter-actuator")
        implementation("org.springframework.boot:spring-boot-starter-security")
        runtimeOnly("org.springframework.boot:spring-boot-devtools")
        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

        // MySQL Dependencies
        implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        runtimeOnly("mysql:mysql-connector-java")

        // MongoDB Dependencies
        implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
        implementation("org.mongodb:mongodb-driver-sync")
        implementation("org.mongodb:mongodb-driver-core")
        implementation("org.mongodb:bson")

        // AMQP Dependencies
        implementation("org.springframework.boot:spring-boot-starter-amqp")

        // JSON Dependencies
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("com.google.code.gson:gson:$gsonVersion")
        implementation("com.google.guava:guava:$guavaVersion")
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")

        // JWT Dependencies
        implementation("io.jsonwebtoken:jjwt:$jjwtVersion")

        // Logging Dependencies
        implementation("org.slf4j:slf4j-api:$slf4jVersion")
        implementation("ch.qos.logback:logback-core:$logbackVersion")
        testImplementation("ch.qos.logback:logback-classic:$logbackVersion")
        implementation("ch.qos.logback.contrib:logback-json-classic:$logbackContribVersion")
        implementation("ch.qos.logback.contrib:logback-jackson:$logbackContribVersion")

        // QueryDSL Dependencies
        implementation("com.querydsl:querydsl-jpa:4.2.1")
        implementation("com.querydsl:querydsl-mongodb")
        kapt(group = "com.querydsl", name = "querydsl-apt", classifier = "jpa")
        sourceSets.main {
            withConvention(org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet::class) {
                kotlin.srcDir("$buildDir/generated/querydsl")
            }
        }

        // Google API Dependencies
        implementation("com.google.api-client:google-api-client:1.35.1")
        implementation("com.google.oauth-client:google-oauth-client-jetty:1.34.1")
        implementation("com.google.apis:google-api-services-calendar:v3-rev20220520-1.32.1")
        implementation("com.google.apis:google-api-services-admin-directory:directory_v1-rev20220606-1.32.1")

        // ETC Dependencies
        implementation("dev.samstevens.totp:totp:$totpVersion")
        implementation("org.mapstruct:mapstruct:$mapstructVersion")
        implementation("io.hypersistence:hypersistence-utils-hibernate-55:$hypersistenceUtilsVesion")
        kapt("org.mapstruct:mapstruct-processor:$mapstructVersion")
        kaptTest("org.mapstruct:mapstruct-processor:$mapstructVersion")

        // Sentry Dependencies
//    implementation("io.sentry:sentry-opentelemetry-agent:$sentryVersion")
//    implementation("io.sentry:sentry-opentelemetry-core:$sentryVersion")

        // Kafka Dependencies
        api("org.springframework.kafka:spring-kafka")

        // AWS Dependencies
        implementation("software.amazon.awssdk:sts:2.17.217")
        implementation("software.amazon.awssdk:s3:2.17.217")
        runtimeOnly("software.amazon.awssdk:bom:2.17.217")
        implementation("software.amazon.awssdk:secretsmanager:2.20.5")
        implementation("aws.sdk.kotlin:ssm:0.17.0-beta")
        implementation("software.amazon.awssdk.crt:aws-crt") {
            version {
                strictly("0.17.0")
            }
        }

        // Swagger Dependencies
        implementation("org.springdoc:springdoc-openapi-ui:$springDocVersion")
        implementation("org.springdoc:springdoc-openapi-kotlin:$springDocVersion")

        // OKta Dependencies
//    implementation("com.okta.spring:okta-spring-boot-starter:$oktaVersion")

        // Coroutines Dependencies
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

        // Detekt
//    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:$detektVersion")

        // okhttp3
//    implementation("com.squareup.okhttp3:okhttp:$okHttpVersion") {
//        version {
//            strictly(okHttpVersion)
//        }
//    }

        // pools
        implementation("org.apache.commons:commons-pool2:$commonsPoolVersion")

        implementation("com.biuea.sportsplatform.file:sdk:1.0.2")
        // Test Dependencies
        testImplementation("io.github.serpro69:kotlin-faker:$kotlinFakerVersion")
        testImplementation("org.amshove.kluent:kluent:$kluentVersion")
        testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoKotlinVersion")
        testImplementation("io.mockk:mockk:$mockkVersion")
        testImplementation("org.junit.jupiter:junit-jupiter:$junitJupiterVersion")
        testImplementation("org.springframework.boot:spring-boot-starter-test") {
            exclude(group = "junit")
            exclude(group = "org.junit.vintage")
        }
        testImplementation("org.springframework.amqp:spring-rabbit-test")
        testImplementation("org.springframework.security:spring-security-test")
        testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo:3.4.5")
        testImplementation("com.squareup.okhttp3:mockwebserver:$okHttpVersion")
        testImplementation("com.squareup.okhttp3:okhttp:$okHttpVersion")
        runtimeOnly("com.h2database:h2")
        implementation("org.testcontainers:mongodb:1.16.0")
    }
}
