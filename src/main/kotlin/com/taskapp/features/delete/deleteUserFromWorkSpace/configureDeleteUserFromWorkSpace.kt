package com.taskapp.features.delete.deleteUserFromWorkSpace

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureDeleteUserFromWorkSpace() {
    val deleteDeleteUserFromWorkSpace = DeleteUserFromWorkSpaceController()
    routing {
        post("/deleteUserFromWorkSpace/{token}/{workSpaceId}/{userLogin}") {
            deleteDeleteUserFromWorkSpace.deleteUserFromWorkSpace(call)
        }
    }
}