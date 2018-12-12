import com.google.protobuf.gradle.*

plugins {
    java
    idea
    id("com.google.protobuf") version "0.8.7"
}

group = "com.gdw"
version = "1.0-SNAPSHOT"

repositories {
    maven("https://plugins.gradle.org/m2/")
}

dependencies {
    testCompile("junit", "junit", "4.12")
    compile("io.netty", "netty-all", "4.1.10.Final")
    compile("com.google.protobuf", "protobuf-java", "3.6.1")
    compile("org.apache.thrift", "libthrift", "0.11.0")
    compile("com.google.protobuf:protobuf-java:3.6.1")
    compile("io.grpc:grpc-stub:1.15.1")
    compile("io.grpc:grpc-protobuf:1.15.1")
    if (JavaVersion.current().isJava9Compatible) {
        compile("javax.annotation:javax.annotation-api:1.3.1")
    }
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.6.1"
    }

    plugins(closureOf<NamedDomainObjectContainer<ExecutableLocator>> {
        this{
            id("grpc") {
                artifact = "io.grpc:protoc-gen-grpc-java:1.15.1"
            }
        }
    })

    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins(closureOf<NamedDomainObjectContainer<GenerateProtoTask.PluginOptions>> {
                this{ id("grpc") }
            })
        }
    }
}
