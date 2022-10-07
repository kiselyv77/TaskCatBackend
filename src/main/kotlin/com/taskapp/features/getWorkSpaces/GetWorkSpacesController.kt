package com.taskapp.features.getWorkSpaces

import com.taskapp.database.tables.tasksToWorkSpaces.TaskToWorkSpacesTable
import com.taskapp.database.tables.tokens.TokensTable
import com.taskapp.database.tables.usersToWorkSpaces.UserToWorkSpacesTable
import com.taskapp.database.tables.workspaces.WorkSpacesTable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*


class GetWorkSpacesController(private val call: ApplicationCall) {

    suspend fun getWorkSpaces() {
        val receive = call.receive<GetWorkSpacesReceiveDTO>()
        println(receive.value)
        val tokens = TokensTable.getTokens()
        val loginUser = tokens.filter { it.token == receive.value }
        if(loginUser.isNotEmpty()){
            val workSpaces = WorkSpacesTable.getWorkSpaces(loginUser.first().login).map{ it ->
                WorkSpacesResponseDTO(
                    id = it.id,
                    name = it.name,
                    description = it.description,
                    creator = it.creator,
                )
            }
            // Отвечаем клиенту
            call.respond(workSpaces)
        }
        else{
            call.respond(HttpStatusCode.BadRequest, "Вы не авторизованны в системе!")
        }
    }

    suspend fun getWorkSpaceById(){
        val receive = call.receive<GetWorkSpaceById>()
        val tokens = TokensTable.getTokens()
        val loginUser = tokens.filter { it.token == receive.token }
        if(loginUser.isNotEmpty()){
            val workSpace = WorkSpacesTable.getWorkSpaceById(receive.workSpaceId)
            if(workSpace != null)  {
                // Получаю юзеров этого рабочего пространства
                val users = UserToWorkSpacesTable.getUserFromWorkSpace(workSpace.id).map { it.userLogin }
                // Получаю таски этого рабочего пространства
                val tasks = TaskToWorkSpacesTable.getTasksFromWorkSpace(workSpace.id).map { it.taskId }
                val workSpaceRespond = WorkSpacesResponseDTO(
                    id = workSpace.id,
                    name = workSpace.name,
                    description = workSpace.description,
                    creator = workSpace.creator,
                    users = users,
                    tasks = tasks
                )
                call.respond(workSpaceRespond)
            }
            else{
                call.respond(HttpStatusCode.BadRequest, "Такого рабочего пространства нет")
            }
        }
        else{
            call.respond(HttpStatusCode.BadRequest, "Вы не авторизованны в системе!")
        }
    }
}