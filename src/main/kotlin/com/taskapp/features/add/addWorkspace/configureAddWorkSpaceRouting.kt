package com.taskapp.features.add.addWorkspace

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureAddWorkSpaceRouting() {
    val addWorkSpaceController = AddWorkSpaceController()
    routing {
        post("/addWorkSpace") {
            addWorkSpaceController.addWorkSpace(call)
        }
    }
}