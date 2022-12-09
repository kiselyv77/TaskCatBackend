package com.taskapp.features.get.getUsers

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureGetUsersRouting() {
    val getUsersController = GetUsersController()
    routing {
        get("/getUsers/{searchQuery}") {
            getUsersController.getAllUsers(call)
        }
        get("/getUserByToken/{token}") {
            getUsersController.getUserByToken(call)
        }
        get("/getUsersFromWorkSpace/{token}/{workSpaceId}") {
            getUsersController.getUsersFromWorkSpace(call)
        }
        get("/getUsersFromTask/{token}/{taskId}"){
            getUsersController.getUsersFromTask(call)
        }
    }
}