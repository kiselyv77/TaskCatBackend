package com.taskapp.features.get.getWorkSpaces

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureGetWorkSpaceRouting() {
    val getWorkSpaceController = GetWorkSpacesController()
    routing {
        get("/getWorkSpaces/{token}") {
            getWorkSpaceController.getWorkSpaces(call)
        }
        get("/getWorkSpaceById/{token}/{id}") {
            getWorkSpaceController.getWorkSpaceById(call)
        }
    }
}