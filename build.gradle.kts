plugins {
    java
}

group = "com.gdw"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testCompile("junit", "junit", "4.12")
    compile("io.netty", "netty-all", "4.1.10.Final")
    compile("com.google.protobuf", "protobuf-java", "3.6.1")
    compile("org.apache.thrift","libthrift", "0.11.0")

}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}