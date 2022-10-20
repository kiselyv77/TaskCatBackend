package com.taskapp.features.setUserValues

import com.taskapp.features.setTaskValues.SetTaskValuesController
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureSetUserStatusRouting() {
    routing {
        post("/setUserStatus/{token}/{newStatus}") {
            SetUserValuesController(call).setUserStatus()
        }
    }
}