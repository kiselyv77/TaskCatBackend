package com.taskapp.features.get.getWorkSpaces

import com.taskapp.database.tables.intermediateTables.tasksToWorkSpaces.TaskToWorkSpacesTable
import com.taskapp.database.tables.mainTables.tokens.TokensTable
import com.taskapp.database.tables.intermediateTables.usersToWorkSpaces.UserToWorkSpacesTable
import com.taskapp.database.tables.mainTables.workspaces.WorkSpacesTable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*


class GetWorkSpacesController() {

    suspend fun getWorkSpaces(call: ApplicationCall) {
        val token = call.parameters["token"]
        val tokens = TokensTable.getTokens()
        val loginUser = tokens.filter { it.token == token }

        if (loginUser.isNotEmpty()) {
            val usersToWorkSpace = UserToWorkSpacesTable.getWorkSpacesForUser(loginUser.first().login)
            println(usersToWorkSpace)
            val workSpacesForUser = usersToWorkSpace.map {
                val workSpace = WorkSpacesTable.getWorkSpaceById(it.workSpacesId)
                    WorkSpacesResponseDTO(
                        id = workSpace?.id.toString(),
                        name = workSpace?.name.toString(),
                        description = workSpace?.description.toString(),
                        creator = workSpace?.creator.toString(),
                    )
            }
            // Отвечаем клиенту
            call.respond(workSpacesForUser)
        } else {
            call.respond(HttpStatusCode.BadRequest, "Вы не авторизованны в системе!")
        }
    }

    suspend fun getWorkSpaceById(call: ApplicationCall) {
        val token = call.parameters["token"]
        val id = call.parameters["id"] ?: ""
        val tokens = TokensTable.getTokens()
        val loginUser = tokens.filter { it.token == token }
        if (loginUser.isNotEmpty()) {
            val workSpace = WorkSpacesTable.getWorkSpaceById(id)
            if (workSpace != null) {
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
            } else {
                call.respond(HttpStatusCode.BadRequest, "Такого рабочего пространства нет")
            }
        } else {
            call.respond(HttpStatusCode.BadRequest, "Вы не авторизованны в системе!")
        }

    }
}