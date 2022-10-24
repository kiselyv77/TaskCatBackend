package com.taskapp.features.add.addUserToTask

import com.taskapp.database.tables.mainTables.tokens.TokensTable
import com.taskapp.database.stringTypes.UserTypes.MEMBER_TYPE
import com.taskapp.database.tables.mainTables.users.UsersTable
import com.taskapp.database.tables.intermediateTables.usersToTasks.UserToTaskDAO
import com.taskapp.database.tables.intermediateTables.usersToTasks.UserToTasksTable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class AddUserToTaskController() {
    suspend fun addTask(call: ApplicationCall) {
        val receive = call.receive<AddUserToTaskReceiveDTO>()
        val tokens = TokensTable.getTokens()
        val loginUser = tokens.filter { it.token == receive.token }
        // Получаем приглашаемого пользователя
        // Если null значит его не существует
        val invitedUser = UsersTable.getUser(receive.userLogin)
        if(loginUser.isNotEmpty()){
            if(invitedUser != null){
                UserToTasksTable.insertUserToTask(
                    UserToTaskDAO(
                        userLogin = receive.userLogin,
                        taskId = receive.taskId,
                        userStatusToTask = MEMBER_TYPE
                    )
                )
                call.respond("Вы пригласили пользователя ${receive.userLogin} в задачу ${receive.taskId}")
            }
            else{
                call.respond(HttpStatusCode.BadRequest, "Пользователя с таким логином не существует в системе")
            }
        }
        else{
            call.respond(HttpStatusCode.BadRequest, "Вы не овтаризованны в системе")
        }

    }
}