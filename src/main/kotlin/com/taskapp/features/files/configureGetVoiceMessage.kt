package com.taskapp.features.files

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureGetVoiceMessage() {
    routing {
        get("/getVoiceMessage/{fileName}") {
            val fileName = call.parameters["fileName"]
            val file = File("C:\\Users\\Mi\\Desktop\\serverFiles\\voiceFiles\\$fileName")
            call.respondFile(file)
        }
    }
}