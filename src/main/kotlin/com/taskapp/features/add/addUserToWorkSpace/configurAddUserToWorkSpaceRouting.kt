package com.taskapp.features.add.addUserToWorkSpace

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureAddUserToWorkSpaceRouting() {
    val addUserToWorkSpaceController = AddUserToWorkSpaceController()
    routing {
        post("/addUserToWorkSpace") {
            addUserToWorkSpaceController.addUserToWorkSpace(call)
        }
    }
}