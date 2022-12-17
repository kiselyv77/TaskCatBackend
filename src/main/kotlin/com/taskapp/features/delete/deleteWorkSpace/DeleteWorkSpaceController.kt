package com.taskapp.features.delete.deleteWorkSpace

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

class DeleteWorkSpaceController() {

    suspend fun deleteWorkSpace(call: ApplicationCall) {
        val token = call.parameters["token"]
        val workSpaceId = call.parameters["workSpaceId"] ?: ""
        val tokens = TokensTable.getTokens()
        val loginUser = tokens.filter { it.token == token }
        if (loginUser.isNotEmpty()) {
            val workSpaceDAO = WorkSpacesTable.getWorkSpaceById(workSpaceId)
            val usersToWorkSpaceDAO = UserToWorkSpacesTable.getUserFromWorkSpace(workSpaceId)
            if(workSpaceDAO != null){
                if(workSpaceDAO.creator == loginUser.last().login || usersToWorkSpaceDAO.last { it.userLogin == loginUser.last().login }.userStatusToWorkSpace == UserTypes.ADMIN_TYPE ){
                    TasksTable.getTasksFromWorkSpace(workSpaceId).forEach{ task -> // Получаем таски этого пространство
                        NotesTable.deleteAllNotesFromTask(task.id) // Удаляем все ноды в тасках
                        UserToTasksTable.deleteAllFromTask(task.id) // Убираем всех пользователей из тасков
                    }
                    TasksTable.deleteAllTasksFromWorkSpace(workSpaceId)  // Удаляем все таски
                    MessagesTable.deleteAllMessagesFromWorkSpace(workSpaceId) // Удаляем все сообщения в пространстве
                    UserToWorkSpacesTable.deleteAllFromWorkSpace(workSpaceId) // Удаляем всех пользователей из пространства
                    WorkSpacesTable.deleteWorkSpace(workSpaceId) // Удаляем пространство
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