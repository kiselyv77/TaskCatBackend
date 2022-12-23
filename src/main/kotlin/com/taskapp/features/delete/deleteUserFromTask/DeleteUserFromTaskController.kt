package com.taskapp.features.delete.deleteUserFromTask

import com.taskapp.database.stringTypes.UserTypes
import com.taskapp.database.tables.intermediateTables.usersToTasks.UserToTasksTable
import com.taskapp.database.tables.intermediateTables.usersToWorkSpaces.UserToWorkSpacesTable
import com.taskapp.database.tables.mainTables.tasks.TasksTable
import com.taskapp.database.tables.mainTables.tokens.TokensTable
import com.taskapp.database.tables.mainTables.workspaces.WorkSpacesTable
import com.taskapp.utils.SucsefullResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

class DeleteUserFromTaskController() {

    suspend fun deleteUserFromTask(call: ApplicationCall) {
        val token = call.parameters["token"]
        val taskId = call.parameters["taskId"] ?: ""
        val userLogin = call.parameters["userLogin"] ?: ""
        val tokens = TokensTable.getTokens().filter { it.token == token }
        if (tokens.isNotEmpty()) {
            val taskDao = TasksTable.getTaskById(taskId)
            if (taskDao != null) {
                val usersToTaskDAO = UserToTasksTable.getUsersFromTask(taskId)
                if (
                    usersToTaskDAO.lastOrNull() { it.userLogin == tokens.lastOrNull()?.login }?.userStatusToTask == UserTypes.CREATOR_TYPE||
                    tokens.lastOrNull()?.login == userLogin
                ) {
                    UserToTasksTable.deleteUserFromTask(taskId, userLogin)
                    call.respond(SucsefullResponse(message = "Sucsefull!)))"))
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Вы не являетесь администратором")
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, "Такого рабочего пространства не существует")
            }
        } else {
            call.respond(HttpStatusCode.BadRequest, "Вы не авторизованы в системе")
        }
    }
}