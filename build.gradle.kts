val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val exposed_version: String by project


plugins {
    application
    kotlin("jvm") version "1.7.21"
    id("io.ktor.plugin") version "2.1.3"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.10"
    id("com.github.johnrengelman.shadow") version "5.2.0"

}

group = "com.taskapp"
version = "0.0.1"
application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

val sshAntTask = configurations.create("sshAntTask")


dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-cio-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    implementation("io.ktor:ktor-server-websockets:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")

    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")

    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")

    implementation("org.postgresql:postgresql:42.3.0")

    sshAntTask("org.apache.ant:ant-jsch:1.10.12")

}

ant.withGroovyBuilder {
    "taskdef"(
        "name" to "scp",
        "classname" to "org.apache.tools.ant.taskdefs.optional.ssh.Scp",
        "classpath" to configurations.get("sshAntTask").asPath
    )
    "taskdef"(
        "name" to "ssh",
        "classname" to "org.apache.tools.ant.taskdefs.optional.ssh.SSHExec",
        "classpath" to configurations.get("sshAntTask").asPath
    )
}

task("deploy") {
    dependsOn("clean", "shadowJar")
    ant.withGroovyBuilder {
        doLast {
            val knownHosts = File.createTempFile("knownhosts", "txt")
            val user = "root"
            val host = "37.252.0.191"
            val password = File("keys/password").readText()
            val jarFileName = "com.taskapp-all.jar"
            try {
                "scp"(
                    "file" to file("build/libs/$jarFileName"),
                    "todir" to "$user@$host:/root/taskcat",
                    "password" to password,
                    "trust" to true,
                    "knownhosts" to knownHosts
                )
                "ssh"(
                    "host" to host,
                    "username" to user,
                    "password" to password,
                    "trust" to true,
                    "knownhosts" to knownHosts,
                    "command" to "mv /root/taskcat/$jarFileName /root/taskcat/taskcat.jar"
                )
                "ssh"(
                    "host" to host,
                    "username" to user,
                    "password" to password,
                    "trust" to true,
                    "knownhosts" to knownHosts,
                    "command" to "systemctl stop taskcat"
                )
                "ssh"(
                    "host" to host,
                    "username" to user,
                    "password" to password,
                    "trust" to true,
                    "knownhosts" to knownHosts,
                    "command" to "systemctl start taskcat"
                )
            } finally {
                knownHosts.delete()
            }
        }
    }
}
