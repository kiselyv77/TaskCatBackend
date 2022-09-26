package com.taskapp.features.getUsers

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureGetUsersRouting() {
    routing {
        get("/getUsers") {
            GetUsersController(call).getAllUsers()
        }
        post("/getUserByToken") {
            GetUsersController(call).getUserByToken()
        }
    }
}