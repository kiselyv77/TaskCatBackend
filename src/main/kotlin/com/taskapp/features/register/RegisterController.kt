package com.taskapp.features.register

import com.taskapp.database.stringTypes.UserStatus
import com.taskapp.database.tables.tokens.TokenDAO
import com.taskapp.database.tables.tokens.TokensTable
import com.taskapp.database.tables.users.UserDAO
import com.taskapp.database.tables.users.UsersTable
import com.taskapp.utils.generateRandomUUID
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class RegisterController(
    private val call: ApplicationCall
) {
    suspend fun registerNewUser() {
        val receive = call.receive<RegisterReceiveDTO>()
        if (receive.login.isNotEmpty() && receive.name.isNotEmpty() && receive.password.isNotEmpty()) {
            val userDTO = UsersTable.getUser(receive.login)
            if (userDTO != null) {
                call.respond(HttpStatusCode.BadRequest, "Пользователь с таким логином уже существует")
            } else {
                UsersTable.insertUser(
                    UserDAO(
                        name = receive.name,
                        login = receive.login,
                        status = UserStatus.ONLINE_STATUS,
                        password = receive.password,
                    )
                )
                val newToken = generateRandomUUID()
                TokensTable.insertToken(
                    TokenDAO(
                        login = receive.login,
                        token = newToken
                    )
                )
                call.respond(RegisterResponseDTO(token = newToken))
            }
        } else {
            call.respond(HttpStatusCode.BadRequest, "Найдены пустве поля!")
        }
    }
}