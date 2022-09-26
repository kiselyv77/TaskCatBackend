package com.taskapp.features.getTasksFromWorkSpace

import com.taskapp.features.getWorkSpaces.GetWorkSpacesController
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureGetTasksFromWorkSpaceRouting (){
    routing {
        get("/getTasksFromWorkSpace") {
            val getTasksFromWorkSpaceController = GetTasksFromWorkSpaceController(call)
            getTasksFromWorkSpaceController.getTasksFromWorkSpace()
        }
    }
}
