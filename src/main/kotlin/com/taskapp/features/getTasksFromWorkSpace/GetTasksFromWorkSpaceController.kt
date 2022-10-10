package com.taskapp.features.getTasksFromWorkSpace

import com.taskapp.database.tables.subTasksToTask.SubTaskToTasksTable
import com.taskapp.database.tables.tasks.TasksTable
import com.taskapp.database.tables.tasksToWorkSpaces.TaskToWorkSpacesTable
import com.taskapp.database.tables.tokens.TokensTable
import com.taskapp.database.tables.usersToTasks.UserToTasksTable
import com.taskapp.database.tables.usersToWorkSpaces.UserToWorkSpacesTable
import com.taskapp.database.tables.workspaces.WorkSpacesTable
import com.taskapp.features.getWorkSpaces.WorkSpacesResponseDTO
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class GetTasksFromWorkSpaceController(private val call: ApplicationCall) {

//    val users = UserToTasksTable.getUsersFromTask(task.id).map { it.userLogin }
//    val subTasks = SubTaskToTasksTable.getSubTasksToTask(task.id).map { it.subTaskId }

    suspend fun getTasksFromWorkSpace() {

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