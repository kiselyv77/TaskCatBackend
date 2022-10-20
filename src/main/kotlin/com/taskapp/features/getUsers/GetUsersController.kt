package com.taskapp.features.getUsers

import com.taskapp.database.tables.tokens.TokensTable
import com.taskapp.database.tables.users.UsersTable
import com.taskapp.database.tables.usersToWorkSpaces.UserToWorkSpacesTable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class GetUsersController(private val call: ApplicationCall) {

    suspend fun getAllUsers() {
        val receive = call.receive<GetUsersReceiveDTO>()
        val users = UsersTable.getUsers(receive.value).map {
            UsersResponseDTO(
                name = it.name,
                status = it.status,
                login = it.login,
            )
        }
        call.respond(users)
    }

    suspend fun getUserByToken() {
        val token = call.parameters["token"]
        val tokens = TokensTable.getTokens()
        val loginUser = tokens.filter { it.token == token }
        val user = UsersTable.getUser(loginUser.first().login)
        if (user != null) {
            call.respond(
                UsersResponseDTO(
                    name = user.name,
                    status = user.status,
                    login = user.login,
                )
            )
        }
        else{
            call.respond(HttpStatusCode.BadRequest, "Такого пользователя не существует")
        }
    }

    suspend fun getUsersFromWorkSpace() {
        val token = call.parameters["token"]
        val workSpaceId = call.parameters["workSpaceId"] ?: ""

        val tokens = TokensTable.getTokens()
        val loginUser = tokens.filter { it.token == token }

        if(loginUser.isNotEmpty()){
            val users = UserToWorkSpacesTable.getUserFromWorkSpace(workSpaceId).map { userToWorkSpaceDAO->
                val user = UsersTable.getUser(userToWorkSpaceDAO.userLogin)
                if(user!=null){
                    UsersResponseDTO(
                        name = user.name,
                        status = user.status,
                        login = user.login,
                    )
                }
                else{
                    call.respond(HttpStatusCode.BadRequest, "Такого пользователя не существует")
                }
            }
            call.respond(users)
        }
        else{
            call.respond(HttpStatusCode.BadRequest, "Вы не авторизованны в системе!")
        }
    }
}