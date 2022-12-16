package com.taskapp.features.delete.deleteWorkSpace

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureDeleteWorkSpace() {
    val deleteWorkSpaceController = DeleteWorkSpaceController()
    routing {
        post("/deleteWorkSpace/{token}/{workSpaceId}") {
            deleteWorkSpaceController.deleteWorkSpace(call)
        }
    }

}