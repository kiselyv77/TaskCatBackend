package com.taskapp.features.add.addUserToTask

import io.ktor.server.application.*
import io.ktor.server.routing.*


fun Application.configureAddUserToTask() {
    val addUserToTaskController = AddUserToTaskController()
    routing {
        post("/addUserToTask") {
            addUserToTaskController.addTask(call)
        }
    }
}