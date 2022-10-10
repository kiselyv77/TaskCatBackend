package com.taskapp.features.getUsers

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureGetUsersRouting() {
    routing {
        get("/getUsers") {
            GetUsersController(call).getAllUsers()
        }
        get("/getUserByToken/{token}") {
            GetUsersController(call).getUserByToken()
        }
    }
}