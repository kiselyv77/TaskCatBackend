package com.taskapp.features.realTime.taskNotes

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureGetNotesFromTask() {
    val getNotesFromTaskController = GetNotesFromTaskController()
    routing {
        get("/getNotesFromTask/{token}/{taskId}") {
            getNotesFromTaskController.getNotes(call)
        }
    }
}