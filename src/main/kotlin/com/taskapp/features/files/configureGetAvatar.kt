package com.taskapp.features.files

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureGetAvatar() {
    routing {
        get("/getAvatar/{login}") {
            val login = call.parameters["login"]
            val file = File("C:\\Users\\Mi\\Desktop\\serverFiles\\avatars\\${login}_avatar.jpg")
            call.respondFile(file)
        }
    }
}