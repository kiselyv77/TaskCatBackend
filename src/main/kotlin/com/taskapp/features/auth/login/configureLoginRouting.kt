package com.taskapp.features.auth.login

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureLoginRouting() {
    val loginController = LoginController()
    routing {
        post("/login") {

            loginController.login(call)
        }
    }
}