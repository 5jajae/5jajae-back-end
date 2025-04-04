import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName

plugins {
	id("org.springframework.boot") version "3.3.0"
	id("io.spring.dependency-management") version "1.1.5"
	kotlin("plugin.jpa") version "1.9.24"
	kotlin("jvm") version "1.9.24"
	kotlin("plugin.spring") version "1.9.24"
	kotlin("kapt") version "1.9.24"
}

base {
	archivesName.set("app")
}

group = "com.ojajae"
version = ""

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
//	runtimeOnly("com.h2database:h2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	// QueryDSL 의존성 추가
	implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
	kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")


	implementation("com.mysql:mysql-connector-j:9.0.0")

	// swagger 설정
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

	// aws
	implementation("io.awspring.cloud:spring-cloud-starter-aws:2.4.4")

	// apache
	implementation("commons-io:commons-io:2.16.1")

	// spring security
	implementation("org.springframework.boot:spring-boot-starter-security")

	// jwt
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
	implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")

	// https://mvnrepository.com/artifact/net.coobird/thumbnailator
	implementation("net.coobird:thumbnailator:0.4.20")


}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
//	useJUnitPlatform()
	exclude("**/*")
}
