package com.taskapp.features.add.addSubTaskToTask

import com.taskapp.database.stringTypes.TaskStatus
import com.taskapp.database.stringTypes.UserTypes
import com.taskapp.database.tables.mainTables.subTasks.SubTaskDAO
import com.taskapp.database.tables.mainTables.subTasks.SubTasksTable
import com.taskapp.database.tables.intermediateTables.subTasksToTask.SubTaskToTaskDAO
import com.taskapp.database.tables.intermediateTables.subTasksToTask.SubTaskToTasksTable
import com.taskapp.database.tables.mainTables.tasks.TasksTable
import com.taskapp.database.tables.mainTables.tokens.TokensTable
import com.taskapp.database.tables.intermediateTables.usersToTasks.UserToTasksTable
import com.taskapp.utils.generateRandomUUID
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class AddSubTaskToTaskController(private val call: ApplicationCall) {

    suspend fun addSubTaskToTask() {
        val receive = call.receive<AddSubTaskToTaskReceiveDTO>()
        val idNewSubTask = generateRandomUUID()
        val tokens = TokensTable.getTokens()
        val loginUser = tokens.filter { it.token == receive.token } // по токену определяем логин
        val task = TasksTable.getTaskById(receive.taskId)

        if (loginUser.isNotEmpty()) {
            if (task != null) {
                val users = UserToTasksTable.getUsersFromTask(receive.taskId)
                if (users.any { it.userLogin == loginUser.first().login }) {
                    SubTasksTable.addSubTask(
                        SubTaskDAO(
                            id = idNewSubTask,
                            name = receive.name,
                            description = receive.description,
                            status = TaskStatus.INPROGRESS_TYPE
                        )
                    )
                    SubTaskToTasksTable.addSubTaskToTask(
                        SubTaskToTaskDAO(
                            taskId = receive.taskId,
                            subTaskId = idNewSubTask
                        )
                    )
                    call.respond("Вы добавили подзадачу в задачу")
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Вы не состоите в этой таске")
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, "Такой таски не существует")
            }
        } else {
            call.respond(HttpStatusCode.BadRequest, "Вы не авторизованны в системе")
        }
    }
}