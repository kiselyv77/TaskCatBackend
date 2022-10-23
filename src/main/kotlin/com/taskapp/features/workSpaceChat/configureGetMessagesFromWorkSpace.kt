package com.taskapp.features.workSpaceChat

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureGetMessagesFromWorkSpace() {
    routing {
        get("/getMessagesFromWorkSpace/{token}/{workSpaceId}") {
            GetMessages(call).getMessagesFromWorkSpace()
        }
    }
}