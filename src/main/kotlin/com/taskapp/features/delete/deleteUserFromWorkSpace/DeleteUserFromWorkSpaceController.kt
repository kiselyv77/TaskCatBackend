package com.taskapp.features.delete.deleteUserFromWorkSpace

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

class DeleteUserFromWorkSpaceController() {
    suspend fun deleteUserFromWorkSpace(call: ApplicationCall) {
        val token = call.parameters["token"]
        val workSpaceId = call.parameters["workSpaceId"] ?: ""
        val userLogin = call.parameters["userLogin"] ?: ""
        val tokens = TokensTable.getTokens().filter { it.token == token }
        if (tokens.isNotEmpty()) {
            val workSpaceDAO = WorkSpacesTable.getWorkSpaceById(workSpaceId)
            if (workSpaceDAO != null) {
                val usersToWorkSpaceDAO = UserToWorkSpacesTable.getUserFromWorkSpace(workSpaceId)
                if (
                    workSpaceDAO.creator == tokens.last().login ||
                    usersToWorkSpaceDAO.last { it.userLogin == tokens.last().login }.userStatusToWorkSpace == UserTypes.ADMIN_TYPE||
                    tokens.last().login == userLogin
                ) {
                    UserToWorkSpacesTable.deleteUserFromWorkSpace(workSpaceId, userLogin)
                    TasksTable.getTasksFromWorkSpace(workSpaceId).forEach{ task ->
                       UserToTasksTable.getTasksForUser(userLogin).forEach{
                           UserToTasksTable.deleteUserFromTask(task.id, it.userLogin) // Удаляю этого пользователя из тасков рабочего пространства
                       }
                    }

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