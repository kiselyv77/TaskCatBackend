package com.taskapp.features.addUserToTask

import io.ktor.server.application.*
import io.ktor.server.routing.*


fun Application.configureAddUserToTask() {
    routing {
        post("/addUserToTask") {
            val addUserToTaskController = AddUserToTaskController(call)
            addUserToTaskController.addTask()
        }
    }
}