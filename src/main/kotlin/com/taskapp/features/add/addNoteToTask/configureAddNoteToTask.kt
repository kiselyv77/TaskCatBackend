package com.taskapp.features.add.addNoteToTask

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureAddNoteToTask() {
    val addNoteToTaskController = AddNoteToTaskController()
    routing {
        post("/addNoteToTask") {
            addNoteToTaskController.addNoteToTask(call)
        }
    }
}