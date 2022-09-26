package com.taskapp.features.getWorkSpaces

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureGetWorkSpaceRouting() {
    routing {
        get("/getWorkSpaces") {
            val getWorkSpaceController = GetWorkSpacesController(call)
            getWorkSpaceController.getWorkSpaces()
        }
    }
}