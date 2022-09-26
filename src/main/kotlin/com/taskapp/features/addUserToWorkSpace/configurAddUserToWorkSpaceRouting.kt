package com.taskapp.features.addUserToWorkSpace

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureAddUserToWorkSpaceRouting() {
    routing {
        post("/addUserToWorkSpace") {
            val controller = AddUserToWorkSpaceController(call)
            controller.addUserToWorkSpace()
        }
    }
}