plugins {
    id ("io.spring.dependency-management")
}

dependencies {
    val springDocVersion: String by project

    implementation(project(":core"))

    implementation("org.springdoc:springdoc-openapi-ui:$springDocVersion")
    implementation("org.springdoc:springdoc-openapi-kotlin:$springDocVersion")
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "11"
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }
}