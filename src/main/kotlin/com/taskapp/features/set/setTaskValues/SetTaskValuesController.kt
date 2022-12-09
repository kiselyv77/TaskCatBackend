package com.taskapp.features.set.setTaskValues

import com.taskapp.database.stringTypes.TaskStatus
import com.taskapp.database.tables.mainTables.tasks.TasksTable
import com.taskapp.database.tables.mainTables.tokens.TokensTable
import com.taskapp.utils.SucsefullResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SetTaskValuesController() {
    suspend fun setTaskStatus(call: ApplicationCall) {
        val token = call.parameters["token"]
        val taskId = call.parameters["taskId"] ?: ""
        val newValue = call.parameters["newStatus"] ?: TaskStatus.INPROGRESS_TYPE
        val tokens = TokensTable.getTokens()
        val loginUser = tokens.filter { it.token == token }
        val task = TasksTable.getTaskById(taskId)
        if (loginUser.isNotEmpty()) {
            if (task != null) {
                TasksTable.setTaskStatus(
                    taskId,
                    newValue
                )
                call.respond(SucsefullResponse(message = "Sucsefull!)))"))
            } else {
                call.respond(HttpStatusCode.BadRequest, "Такой таски не существует")
            }
        } else {
            call.respond(HttpStatusCode.BadRequest, "Вы не авторизованы в системе")
        }

    }

    suspend fun setTaskName(call: ApplicationCall) {
        val token = call.parameters["token"]
        val taskId = call.parameters["taskId"] ?: ""
        val newName = call.parameters["newName"] ?: ""
        val tokens = TokensTable.getTokens()
        val loginUser = tokens.filter { it.token == token }
        val task = TasksTable.getTaskById(taskId)
        if (loginUser.isNotEmpty()) {
            if (task != null) {
                TasksTable.setTaskName(
                    taskId = taskId,
                    newName = newName
                )
                call.respond("SUCSEFULL")
            } else {
                call.respond(HttpStatusCode.BadRequest, "Такой таски не существует")
            }
        } else {
            call.respond(HttpStatusCode.BadRequest, "Вы не авторизованы в системе")
        }

    }

    suspend fun setTaskDescription(call: ApplicationCall) {
        val token = call.parameters["token"]
        val taskId = call.parameters["taskId"] ?: ""
        val newDescription = call.parameters["newDescription"] ?: ""
        val tokens = TokensTable.getTokens()
        val loginUser = tokens.filter { it.token == token }
        val task = TasksTable.getTaskById(taskId)
        if (loginUser.isNotEmpty()) {
            if (task != null) {
                TasksTable.setTaskDescription(
                    taskId = taskId,
                    newDescription = newDescription
                )
                call.respond("SUCSEFULL")
            } else {
                call.respond(HttpStatusCode.BadRequest, "Такой таски не существует")
            }
        } else {
            call.respond(HttpStatusCode.BadRequest, "Вы не авторизованы в системе")
        }

    }

    suspend fun setTaskDeadLine(call: ApplicationCall) {
        val token = call.parameters["token"]
        val taskId = call.parameters["taskId"] ?: ""
        val newDeadLine = call.parameters["newDeadLine"] ?: LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
        val tokens = TokensTable.getTokens()
        val loginUser = tokens.filter { it.token == token }
        val task = TasksTable.getTaskById(taskId)
        if (loginUser.isNotEmpty()) {
            if (task != null) {
                TasksTable.setTaskDeadLine(
                    taskId,
                    newDeadLine
                )
                call.respond(SucsefullResponse(message = "Sucsefull!)))"))
            } else {
                call.respond(HttpStatusCode.BadRequest, "Такой таски не существует")
            }
        } else {
            call.respond(HttpStatusCode.BadRequest, "Вы не авторизованы в системе")
        }
    }
}