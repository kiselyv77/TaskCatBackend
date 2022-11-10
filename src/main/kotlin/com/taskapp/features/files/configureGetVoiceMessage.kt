package com.taskapp.features.files

import com.taskapp.database.tables.mainTables.tokens.TokensTable
import com.taskapp.utils.SucsefullResponse
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureGetVoiceMessage() {
    routing {
        get("/getAvatar/{login}{fileName}") {
            val login = call.parameters["login"]
            val fileName = call.parameters["fileName"]
            val file = File("C:\\Users\\Mi\\Desktop\\serverFiles\\voiceFiles\\$login\\$fileName")
            call.respondFile(file)
        }
    }
}