package com.taskapp.features.add.addTaskToWorkSpace

import com.taskapp.utils.getIsoDateTime
import com.taskapp.database.stringTypes.TaskStatus.INPROGRESS_TYPE
import com.taskapp.database.tables.mainTables.tasks.TaskDAO
import com.taskapp.database.tables.mainTables.tasks.TasksTable
import com.taskapp.database.tables.mainTables.tokens.TokensTable
import com.taskapp.database.stringTypes.UserTypes.ADMIN_TYPE
import com.taskapp.database.stringTypes.UserTypes.CREATOR_TYPE
import com.taskapp.database.stringTypes.UserTypes.MEMBER_TYPE
import com.taskapp.database.tables.intermediateTables.usersToTasks.UserToTaskDAO
import com.taskapp.database.tables.intermediateTables.usersToTasks.UserToTasksTable
import com.taskapp.database.tables.intermediateTables.usersToWorkSpaces.UserToWorkSpacesTable
import com.taskapp.database.tables.mainTables.workspaces.WorkSpacesTable
import com.taskapp.features.get.getTasksFromWorkSpace.GetTasksFromWorkSpaceResponseDTO
import com.taskapp.utils.generateRandomUUID
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class AddTaskToWorkSpaceController() {
    suspend fun addTask(call: ApplicationCall) {
        val receive = call.receive<AddTaskToWorkSpaceReceiveDTO>()
        val newTaskId = generateRandomUUID()
        val tokens = TokensTable.getTokens()
        val loginUser = tokens.filter { it.token == receive.token } // по токену определяем логин
        if (loginUser.isNotEmpty()) {
            val workSpace = WorkSpacesTable.getWorkSpaceById(receive.workSpaceId)
            val user = UserToWorkSpacesTable.getUserFromWorkSpace(receive.workSpaceId)
                .first { it.userLogin == loginUser.first().login }
            // сдесь проверим является ли пользователь отправляющий запрос создателем или администратором
            if (workSpace?.creator == loginUser.first().login || user.userStatusToWorkSpace == ADMIN_TYPE) {
                println("!-----------------------------!---- ${receive.userList}")
                val taskDAO = TaskDAO(
                    id = newTaskId, // айди новой таски
                    workSpaceId= receive.workSpaceId,
                    name = receive.name,
                    description = receive.description,
                    status = INPROGRESS_TYPE,
                    deadLine = receive.deadLine,
                    creationDate = System.currentTimeMillis().toString()
                )
                TasksTable.insertTable(
                    taskDAO
                )

                UserToTasksTable.insertUserToTask(
                    UserToTaskDAO(
                        userLogin = loginUser.first().login,
                        taskId = newTaskId, // таски содержат пользователей // сразу добавим создателя
                        userStatusToTask = CREATOR_TYPE // его статус CREATOR_TYPE
                    )
                )
                receive.userList.forEach { login -> // добавим остальных
                    UserToTasksTable.insertUserToTask(
                        UserToTaskDAO(
                            userLogin = login,
                            taskId = newTaskId,
                            userStatusToTask = MEMBER_TYPE // его статус MEMBER_TYPE
                        )
                    )
                }
                call.respond(
                    GetTasksFromWorkSpaceResponseDTO(
                        id = taskDAO.id,
                        name = taskDAO.name,
                        description = taskDAO.description,
                        taskStatus = taskDAO.status,
                        deadLine = taskDAO.deadLine,
                        creationDate = taskDAO.creationDate
                    )
                )
            } else {
                call.respond(HttpStatusCode.BadRequest, "Вы не являетесь администратором этого рабочего пространства")
            }
        } else {
            call.respond(HttpStatusCode.BadRequest, "Вы не авторизованны в системе")
        }
    }
}