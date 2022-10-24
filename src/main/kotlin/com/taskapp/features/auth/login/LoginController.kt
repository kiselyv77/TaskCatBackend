package com.taskapp.features.auth.login

import com.taskapp.database.stringTypes.UserStatus
import com.taskapp.database.tables.mainTables.tokens.TokenDAO
import com.taskapp.database.tables.mainTables.tokens.TokensTable
import com.taskapp.database.tables.mainTables.users.UsersTable
import com.taskapp.utils.generateRandomUUID
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class LoginController() {
    suspend fun login(call: ApplicationCall) {
        val receive = call.receive<LoginReceiveDTO>()

        val userDTO = UsersTable.getUser(receive.login)
        if (userDTO == null) {
            call.respond(HttpStatusCode.BadRequest, "пользователь не найден")
        } else {
            if (userDTO.password == receive.password) {
                val token = generateRandomUUID()
                TokensTable.insertToken(
                    TokenDAO(
                        login = receive.login,
                        token = token
                    )
                )
                UsersTable.setUserStatus(receive.login, UserStatus.ONLINE_STATUS)
                call.respond(LoginResponseDTO(token = token))
            } else {
                call.respond(HttpStatusCode.BadRequest, "Неправильный пароль")
            }
        }
    }

}