package com.taskapp.features.login

import com.taskapp.database.tables.tokens.TokenDAO
import com.taskapp.database.tables.tokens.TokensTable
import com.taskapp.database.tables.users.UsersTable
import com.taskapp.utils.generateRandomUUID
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class LoginController(
    private val call: ApplicationCall
) {
    suspend fun login() {
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
                call.respond(LoginResponseDTO(token = token))
            } else {
                call.respond(HttpStatusCode.BadRequest, "Неправильный пароль")
            }
        }
    }

}