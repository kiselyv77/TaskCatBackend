package com.taskapp.features.addTaskToWorkSpace

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureAddTaskToWorkSpaceRouting() {
    routing {
        post("/addTaskToWorkSpace") {
            val addTaskToWorkSpaceController = AddTaskToWorkSpaceController(call)
            addTaskToWorkSpaceController.addTask()
        }
    }
}