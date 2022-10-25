package com.taskapp.features.get.getTasksFromWorkSpace

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureGetTasksFromWorkSpaceRouting (){
    val getTasksFromWorkSpaceController = GetTasksFromWorkSpaceController()
    routing {
        get("/getTasksFromWorkSpace/{token}/{workSpaceId}") {
            getTasksFromWorkSpaceController.getTasksFromWorkSpace(call)
        }
        get("/getTaskById/{token}/{id}") {
            getTasksFromWorkSpaceController.getTaskById(call)
        }
    }
}
