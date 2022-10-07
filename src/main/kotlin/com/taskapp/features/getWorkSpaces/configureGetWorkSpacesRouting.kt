package com.taskapp.features.getWorkSpaces

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureGetWorkSpaceRouting() {
    routing {
        post("/getWorkSpaces") {
            val getWorkSpaceController = GetWorkSpacesController(call)
            getWorkSpaceController.getWorkSpaces()
        }
        post("/getWorkSpaceById") {
            val getWorkSpaceController = GetWorkSpacesController(call)
            getWorkSpaceController.getWorkSpaceById()
        }
    }
}