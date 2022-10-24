package com.taskapp.features.add.addSubTaskToTask

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureAddSubTaskToTaskRouting() {
    routing {
        post("/addSubTaskToTask") {
            val addSubTaskToTaskController = AddSubTaskToTaskController(call)
            addSubTaskToTaskController.addSubTaskToTask()
        }
    }
}