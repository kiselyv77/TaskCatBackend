package com.taskapp.features.getTasksFromWorkSpace

import com.taskapp.database.tables.subTasksToTask.SubTaskToTasksTable
import com.taskapp.database.tables.tasks.TasksTable
import com.taskapp.database.tables.tasksToWorkSpaces.TaskToWorkSpacesTable
import com.taskapp.database.tables.usersToTasks.UserToTasksTable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class GetTasksFromWorkSpaceController(private val call: ApplicationCall) {

    suspend fun getTasksFromWorkSpace() {

        val receive = call.receive<GetTasksFromWorkSpaceReceiveDTO>()

        val tasks = TaskToWorkSpacesTable.getTasksFromWorkSpace(receive.workSpaceId).map { taskToWorkSpaceDAO ->
            val task = TasksTable.getTaskById(taskToWorkSpaceDAO.taskId)
            if(task != null){
                val users = UserToTasksTable.getUsersFromTask(task.id).map { it.userLogin }
                val subTasks = SubTaskToTasksTable.getSubTasksToTask(task.id).map { it.subTaskId }
                GetTasksFromWorkSpaceResponseDTO(
                    id = task.id,
                    name = task.name,
                    description = task.description,
                    users = users,
                    subTask = subTasks,
                    taskStatus = task.status
                )
            }
            else{
                null
            }
        }
        call.respond(tasks)
    }
}