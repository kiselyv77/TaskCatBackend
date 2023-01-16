package com.taskapp.features.files

import com.taskapp.utils.getFileSeparator
import com.taskapp.utils.getRootPackage
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureGetAvatar() {
    routing {
        get("/getAvatar/{login}") {
            val login = call.parameters["login"]
            val sep = getFileSeparator()
            val file = File("${getRootPackage()}${sep}avatars${sep}${login}_avatar.jpg")
            call.respondFile(file)
        }
    }
}