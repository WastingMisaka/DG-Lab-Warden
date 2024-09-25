plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.17.3"
}

group = "com.WastingMisaka"
version = "1.0.3"

repositories {
    mavenCentral()
}

intellij {
    version.set("2023.2.6")
    type.set("IC") // Target IDE Platform IC/IU

    plugins.set(listOf(/* Plugin Dependencies */))
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }

    patchPluginXml {
        sinceBuild.set("232")
        untilBuild.set("242.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }

    dependencies{
        implementation("org.apache.tomcat.embed:tomcat-embed-websocket:10.1.28")
        implementation("org.eclipse.jetty:jetty-server:9.4.43.v20210629")
        implementation("org.eclipse.jetty.websocket:websocket-server:9.4.43.v20210629")
        implementation("org.eclipse.jetty.websocket:javax-websocket-server-impl:9.4.43.v20210629")
        implementation("com.google.zxing:core:3.5.1")
        implementation("com.google.zxing:javase:3.5.1")
        implementation ("org.jetbrains:annotations:23.0.0")
    }
}
