package com.taskapp.features.files

import com.taskapp.database.tables.mainTables.tokens.TokensTable
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureGetAvatar() {
    routing {
        get("/getAvatar/{login}") {
            val login = call.parameters["login"]
            val file = File("C:\\Users\\Mi\\Desktop\\serverFiles\\${login}_avatar.jpg")
            call.respondFile(file)
        }
    }
}