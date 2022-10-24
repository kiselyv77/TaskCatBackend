package com.taskapp.features.set.setTaskValues

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureSetTaskValuesRouting() {
    val setTaskValuesController = SetTaskValuesController()
    routing {
        post("/setTaskStatus/{token}/{taskId}/{newStatus}") {
            setTaskValuesController.setTaskStatus(call)
        }
        post("/setTaskName/{token}/{taskId}/{newValue}") {
            setTaskValuesController.setTaskName(call)
        }
        post("/setTaskDescription/{token}/{taskId}/{newValue}") {
            setTaskValuesController.setTaskDescription(call)
        }
    }
}
