package com.taskapp.features.getWorkSpaces

import com.taskapp.database.tables.tasksToWorkSpaces.TaskToWorkSpacesTable
import com.taskapp.database.tables.usersToWorkSpaces.UserToWorkSpacesTable
import com.taskapp.database.tables.workspaces.WorkSpacesTable
import io.ktor.server.application.*
import io.ktor.server.response.*


class GetWorkSpacesController(private val call: ApplicationCall) {

    suspend fun getWorkSpaces() {
        val workSpaces = WorkSpacesTable.getWorkSpaces().map{ it ->
            // Получаю юзеров этого рабочего пространства
            val users = UserToWorkSpacesTable.getUserFromWorkSpace(it.id).map { it.userLogin }
            // Получаю таски этого рабочего пространства
            val tasks = TaskToWorkSpacesTable.getTasksFromWorkSpace(it.id).map { it.taskId }

            // Ответ вставляем туда данные которые получаем из других таблиц (списки и тд)
            WorkSpacesResponseDTO(
                id = it.id,
                name = it.name,
                description = it.description,
                creator = it.creator,
                users = users,
                tasks = tasks
            )
        }
        // Отвечаем клиенту
        call.respond(workSpaces)
    }

}