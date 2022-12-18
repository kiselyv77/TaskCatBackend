package com.taskapp.features.delete.deleteTask

import com.taskapp.database.stringTypes.UserTypes
import com.taskapp.database.tables.intermediateTables.usersToTasks.UserToTasksTable
import com.taskapp.database.tables.intermediateTables.usersToWorkSpaces.UserToWorkSpacesTable
import com.taskapp.database.tables.mainTables.messages.MessagesTable
import com.taskapp.database.tables.mainTables.notes.NotesTable
import com.taskapp.database.tables.mainTables.tasks.TasksTable
import com.taskapp.database.tables.mainTables.tokens.TokensTable
import com.taskapp.database.tables.mainTables.workspaces.WorkSpacesTable
import com.taskapp.utils.SucsefullResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

class DeleteTaskController() {

    suspend fun deleteTask(call: ApplicationCall) {
        val token = call.parameters["token"]
        val taskId = call.parameters["taskId"] ?: ""
        val tokens = TokensTable.getTokens().filter { it.token == token }
        if (tokens.isNotEmpty()) {
            val taskDAO = TasksTable.getTaskById(taskId)
            val usersToTaskDAO = UserToTasksTable.getUsersFromTask(taskId)
            if(taskDAO != null){
                val userStatusToTask = usersToTaskDAO.last { it.userLogin == tokens.last().login }.userStatusToTask
                if(userStatusToTask == UserTypes.ADMIN_TYPE || userStatusToTask == UserTypes.CREATOR_TYPE){
                    NotesTable.deleteAllNotesFromTask(taskId) // Удаляем все ноды в тасках
                    UserToTasksTable.deleteAllFromTask(taskId) // Убираем всех пользователей из тасков
                    TasksTable.deleteTask(taskId)
                    call.respond(SucsefullResponse(message = "Sucsefull!)))"))
                }
                else{
                    call.respond(HttpStatusCode.BadRequest, "Вы не являетесь администратором")
                }
            }
            else{
                call.respond(HttpStatusCode.BadRequest, "Такого рабочего пространства не существует")
            }
        }
        else{
            call.respond(HttpStatusCode.BadRequest, "Вы не авторизованы в системе")

        }
    }
}