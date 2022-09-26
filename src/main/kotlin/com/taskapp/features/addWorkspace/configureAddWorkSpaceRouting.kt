package com.taskapp.features.addWorkspace

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureAddWorkSpaceRouting() {
    routing {
        post("/addWorkSpace") {
            val addWorkSpaceController = AddWorkSpaceController(call)
            addWorkSpaceController.addWorkSpace()
        }
    }
}