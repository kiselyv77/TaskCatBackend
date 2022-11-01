package com.taskapp.features.files

import com.taskapp.database.tables.mainTables.tokens.TokensTable
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureGetAvatar() {
    routing {
        get("/getAvatar/{token}") {
            val token = call.parameters["token"]
            val tokens = TokensTable.getTokens()
            val loginUser = tokens.last { it.token == token }.login // по токену определяем логин

            val file = File("C:\\Users\\Mi\\Desktop\\serverFiles\\${loginUser}_avatar.jpg")

            call.respondFile(file)
        }
    }
}