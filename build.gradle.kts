import com.google.protobuf.gradle.id

plugins {
	java
	id("org.springframework.boot") version "3.1.5"
	id("io.spring.dependency-management") version "1.1.6"
	id("com.google.protobuf") version "0.9.4"
}

group = "com.programming"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.google.protobuf:protobuf-java:3.6.1")
	implementation("io.grpc:grpc-stub:1.15.1")
	implementation("io.grpc:grpc-protobuf:1.15.1")
	implementation("org.modelmapper:modelmapper:2.1.1")
	implementation(platform("io.awspring.cloud:spring-cloud-aws-dependencies:3.1.0"))
	implementation("io.awspring.cloud:spring-cloud-aws-starter")
	implementation ("io.awspring.cloud:spring-cloud-aws-starter-dynamodb")
	implementation ("io.awspring.cloud:spring-cloud-aws-starter-sqs")

	compileOnly("org.projectlombok:lombok:1.18.30")
	annotationProcessor("org.projectlombok:lombok:1.18.30")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	protobuf(files("lib/protos.tar.gz"))
	protobuf(files("ext/"))
	testProtobuf(files("lib/protos-test.tar.gz"))
}

protobuf {
	protoc {
		artifact = "com.google.protobuf:protoc:3.6.1"
	}
	plugins {
		id("grpc") {
			artifact = "io.grpc:protoc-gen-grpc-java:1.15.1"
		}
	}
	generateProtoTasks {
		ofSourceSet("main").forEach {
			it.plugins {
				id("grpc") { }
			}
		}
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}