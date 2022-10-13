package com.taskapp.features.setTaskValues

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureSetTaskValuesRouting() {
    routing {
        post("/setTaskStatus/{token}/{taskId}/{newStatus}") {
            SetTaskValuesController(call).setTaskStatus()
        }
        post("/setTaskName/{token}/{taskId}/{newValue}") {
            SetTaskValuesController(call).setTaskName()
        }
        post("/setTaskDescription/{token}/{taskId}/{newValue}") {
            SetTaskValuesController(call).setTaskDescription()
        }
    }
}
