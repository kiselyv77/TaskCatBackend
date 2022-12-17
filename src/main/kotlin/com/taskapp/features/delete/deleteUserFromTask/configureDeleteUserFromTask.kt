package com.taskapp.features.delete.deleteUserFromTask

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureDeleteUserFromTask() {
    val deleteUserFromTaskController = DeleteUserFromTaskController()
    routing {
        post("/deleteUserFromTask/{token}/{taskId}/{userLogin}") {
            deleteUserFromTaskController.deleteUserFromTask(call)
        }
    }
}