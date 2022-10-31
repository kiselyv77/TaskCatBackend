package com.taskapp.features.files

import com.taskapp.utils.SucsefullResponse
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureUploadNewAvatar() {
    routing {
        post("/uploadNewAvatar") {
            call.respond(SucsefullResponse("asdasdasdads"))
        }
    }
}