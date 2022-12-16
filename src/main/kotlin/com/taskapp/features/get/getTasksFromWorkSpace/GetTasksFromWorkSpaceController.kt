package com.taskapp.features.get.getTasksFromWorkSpace

import com.taskapp.database.stringTypes.TaskStatus
import com.taskapp.database.tables.mainTables.tasks.TasksTable
import com.taskapp.database.tables.mainTables.tokens.TokensTable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class GetTasksFromWorkSpaceController() {

    suspend fun getTasksFromWorkSpace(call: ApplicationCall) {

        val token = call.parameters["token"]
        val workSpaceId = call.parameters["workSpaceId"] ?: ""
        val tokens = TokensTable.getTokens()
        val loginUser = tokens.filter { it.token == token }

        if (loginUser.isNotEmpty()) {
            val tasks = TasksTable.getTasksFromWorkSpace(workSpaceId).map { task ->
                GetTasksFromWorkSpaceResponseDTO(
                    id = task.id,
                    name = task.name,
                    description = task.description,
                    taskStatus = task.status,
                    deadLine = task.deadLine,
                    creationDate = task.creationDate
                )
            }
            call.respond(tasks)
        } else {
            call.respond(HttpStatusCode.BadRequest, "Вы не авторизованны в системе!")
        }

    }

    suspend fun getTaskById(call: ApplicationCall) {
        val token = call.parameters["token"]
        val id = call.parameters["id"] ?: ""
        val tokens = TokensTable.getTokens()
        val loginUser = tokens.filter { it.token == token }
        if (loginUser.isNotEmpty()) {
            val task = TasksTable.getTaskById(id)
            if (task != null) {
                val taskRespond = GetTasksFromWorkSpaceResponseDTO(
                    id = task.id,
                    name = task.name,
                    description = task.description,
                    taskStatus = task.status,
                    deadLine = task.deadLine,
                    creationDate = task.creationDate
                )
                call.respond(taskRespond)
            } else {
                call.respond(HttpStatusCode.BadRequest, "Такой задачи нет")
            }
        } else {
            call.respond(HttpStatusCode.BadRequest, "Вы не авторизованны в системе!")
        }

    }

}