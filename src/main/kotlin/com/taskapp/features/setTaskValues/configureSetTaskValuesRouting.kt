package com.taskapp.features.setTaskValues

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureSetTaskValuesRouting() {
    routing {
        post("/setTaskStatus") {
            SetTaskValuesController(call).setTaskStatus()
        }
        post("/setTaskName") {
            SetTaskValuesController(call).setTaskName()
        }
        post("/setTaskDescription") {
            SetTaskValuesController(call).setTaskDescription()
        }
    }
}
