package com.taskapp.features.add.addTaskToWorkSpace

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureAddTaskToWorkSpaceRouting() {
    val addTaskToWorkSpaceController = AddTaskToWorkSpaceController()
    routing {
        post("/addTaskToWorkSpace") {

            addTaskToWorkSpaceController.addTask(call)
        }
    }
}