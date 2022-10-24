package com.taskapp.features.set.setUserValues

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureSetUserStatusRouting() {
    val setUserValuesController = SetUserValuesController()
    routing {
        post("/setUserStatus/{token}/{newStatus}") {
            setUserValuesController.setUserStatus(call)
        }
    }
}