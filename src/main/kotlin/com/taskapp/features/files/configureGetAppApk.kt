package com.taskapp.features.files

import com.taskapp.utils.getFileSeparator
import com.taskapp.utils.getRootPackage
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureGetAppApk() {
    routing {
        get("/getAppApk") {
            val sep = getFileSeparator()
            val file = File("${getRootPackage()}${sep}apk${sep}task-cat.apk")
            call.response.header("Content-Disposition", "attachment; filename=\"${file.name}\"")
            call.respondFile(file)
        }
    }
}