package com.taskapp.features.add.addUserToTask

import com.taskapp.database.tables.mainTables.tokens.TokensTable
import com.taskapp.database.stringTypes.UserTypes.MEMBER_TYPE
import com.taskapp.database.tables.mainTables.users.UsersTable
import com.taskapp.database.tables.intermediateTables.usersToTasks.UserToTaskDAO
import com.taskapp.database.tables.intermediateTables.usersToTasks.UserToTasksTable
import com.taskapp.database.tables.intermediateTables.usersToWorkSpaces.UserToWorkSpacesTable
import com.taskapp.utils.SucsefullResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class AddUserToTaskController() {
    suspend fun addTask(call: ApplicationCall) {
        val token = call.parameters["token"]?: ""
        val userLogin = call.parameters["userLogin"]?: ""
        val taskId = call.parameters["taskId"]?: ""
        val tokens = TokensTable.getTokens()
        val loginUser = tokens.filter { it.token == token }
        // Получаем приглашаемого пользователя
        // Если null значит его не существует
        val invitedUser = UsersTable.getUser(userLogin)
        if(loginUser.isNotEmpty()){
            if(invitedUser != null){
                if(UserToWorkSpacesTable.getUserFromWorkSpace(taskId).none { it.userLogin == invitedUser?.login }){
                    UserToTasksTable.insertUserToTask(
                        UserToTaskDAO(
                            userLogin = userLogin,
                            taskId = taskId,
                            userStatusToTask = MEMBER_TYPE
                        )
                    )
                    call.respond(SucsefullResponse(message = "Sucsesfull"))
                }
                else{
                    call.respond(HttpStatusCode.BadRequest, "Пользователь уже работает над этой задачей")
                }

            }
            else{
                call.respond(HttpStatusCode.BadRequest, "Пользователь не найден")
            }
        }
        else{
            call.respond(HttpStatusCode.BadRequest, "Вы не овтаризованны в системе")
        }

    }
}