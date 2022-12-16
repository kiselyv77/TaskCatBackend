package com.taskapp.features.delete.deleteTask

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureDeleteTask() {
    val deleteTaskController = DeleteTaskController()
    routing {


        post("/deleteWorkSpace/{token}/{workSpaceId}") {


            deleteTaskController.deleteTask(call)
        }
    }

}