package com.taskapp.features.get.getTasksFromWorkSpace

import com.taskapp.database.tables.mainTables.tasks.TasksTable
import com.taskapp.database.tables.intermediateTables.tasksToWorkSpaces.TaskToWorkSpacesTable
import com.taskapp.database.tables.mainTables.tokens.TokensTable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

class GetTasksFromWorkSpaceController() {

    suspend fun getTasksFromWorkSpace(call: ApplicationCall) {

        val token = call.parameters["token"]
        val workSpaceId = call.parameters["workSpaceId"] ?: ""
        val tokens = TokensTable.getTokens()
        val loginUser = tokens.filter { it.token == token }

        if(loginUser.isNotEmpty()){
            val tasks = TaskToWorkSpacesTable.getTasksFromWorkSpace(workSpaceId).map { taskToWorkSpaceDAO ->
                val task = TasksTable.getTaskById(taskToWorkSpaceDAO.taskId)
                if(task != null){
                    GetTasksFromWorkSpaceResponseDTO(
                        id = task.id,
                        name = task.name,
                        description = task.description,
                        users = emptyList(),
                        subTask = emptyList(),
                        taskStatus = task.status
                    )
                }
                else{
                    call.respond(HttpStatusCode.BadRequest, "Такой задачи не существует")
                }
            }
            call.respond(tasks)
        }
        else{
            call.respond(HttpStatusCode.BadRequest, "Вы не авторизованны в системе!")
        }

    }
}