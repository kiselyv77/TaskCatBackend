package com.taskapp.features.get.getNotesFromTask

import com.taskapp.features.add.addNoteToTask.AddNoteToTaskController
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureGetNotesFromTask() {
    val getNotesFromTaskController = GetNotesFromTaskController()
    routing {
        post("/addNoteToTask") {
            getNotesFromTaskController.getNotes(call)
        }
    }
}