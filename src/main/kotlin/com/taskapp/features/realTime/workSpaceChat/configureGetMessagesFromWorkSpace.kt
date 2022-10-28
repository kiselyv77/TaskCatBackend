package com.taskapp.features.realTime.workSpaceChat

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureGetMessagesFromWorkSpace() {
    val getMessagesController = GetMessagesController()
    routing {
        get("/getMessagesFromWorkSpace/{token}/{workSpaceId}/{offset}") {
            getMessagesController.getMessagesFromWorkSpace(call)
        }
    }
}