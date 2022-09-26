package com.taskapp.features.addTaskToWorkSpace

import com.taskapp.database.stringTypes.TaskStatus
import com.taskapp.database.stringTypes.TaskStatus.INPROGRESS_TYPE
import com.taskapp.database.tables.tasks.TaskDAO
import com.taskapp.database.tables.tasks.TasksTable
import com.taskapp.database.tables.tasksToWorkSpaces.TaskToWorkSpaceDAO
import com.taskapp.database.tables.tasksToWorkSpaces.TaskToWorkSpacesTable
import com.taskapp.database.tables.tokens.TokensTable
import com.taskapp.database.stringTypes.UserTypes.ADMIN_TYPE
import com.taskapp.database.stringTypes.UserTypes.CREATOR_TYPE
import com.taskapp.database.tables.usersToTasks.UserToTaskDAO
import com.taskapp.database.tables.usersToTasks.UserToTasksTable
import com.taskapp.database.tables.usersToWorkSpaces.UserToWorkSpacesTable
import com.taskapp.database.tables.workspaces.WorkSpacesTable
import com.taskapp.utils.generateRandomUUID
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class AddTaskToWorkSpaceController(val call: ApplicationCall) {
    suspend fun addTask() {
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
                TasksTable.insertTable(
                    TaskDAO(
                        id = newTaskId, // айди новой таски
                        name = receive.name,
                        description = receive.description,
                        status = TaskStatus.INPROGRESS_TYPE
                    )
                )
                TaskToWorkSpacesTable.insertTaskToWorkSpace(
                    TaskToWorkSpaceDAO(
                        taskId = newTaskId, // рабочее пространство содержит таски //добавим
                        workSpaceId = receive.workSpaceId
                    )
                )
                UserToTasksTable.insertUserToTask(
                    UserToTaskDAO(
                        userLogin = loginUser.first().login,
                        taskId = newTaskId, // таски содержат пользователей // сразу добавим создателя
                        userStatusToTask = CREATOR_TYPE // его статус CREATOR_TYPE
                    )
                )
                call.respond("Вы добавили задачу $newTaskId в рабочее пространство ${receive.workSpaceId}")
            } else {
                call.respond(HttpStatusCode.BadRequest, "Вы не являетесь администратором этого рабочего пространства")
            }
        } else {
            call.respond(HttpStatusCode.BadRequest, "Вы не авторизованны в системе")
        }
    }
}