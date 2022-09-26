package com.taskapp.features.getUsers

import com.taskapp.database.tables.tokens.TokensTable
import com.taskapp.database.tables.users.UsersTable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class GetUsersController(private val call: ApplicationCall) {

    suspend fun getAllUsers() {
        val receive = call.receive<GetUsersReceiveDTO>()
        val users = UsersTable.getUsers(receive.value).map {
            UsersResponseDTO(
                name = it.name,
                login = it.login,
            )
        }
        call.respond(users)
    }

    suspend fun getUserByToken() {
        val receive = call.receive<GetUsersReceiveDTO>()
        val tokens = TokensTable.getTokens()
        val loginUser = tokens.filter { it.token == receive.value }
        val user = UsersTable.getUser(loginUser.first().login)
        if (user != null) {
            call.respond(
                UsersResponseDTO(
                    user.name,
                    user.login
                )
            )
        }
        else{
            call.respond(HttpStatusCode.BadRequest, "Такого пользователя не существует")
        }
    }

    suspend fun getUsersFromWorkSpace() {

    }
}