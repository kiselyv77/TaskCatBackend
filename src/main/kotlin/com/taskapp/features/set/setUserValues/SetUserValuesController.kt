package com.taskapp.features.set.setUserValues

import com.taskapp.database.stringTypes.UserStatus
import com.taskapp.database.tables.mainTables.tokens.TokensTable
import com.taskapp.database.tables.mainTables.users.UsersTable
import com.taskapp.utils.SucsefullResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

class SetUserValuesController() {

    suspend fun setUserStatus(call: ApplicationCall) {
        val token = call.parameters["token"] ?: ""
        val newStatus = call.parameters["newStatus"] ?: UserStatus.OFFLINE_STATUS
        val tokens = TokensTable.getTokens()
        val loginUser = tokens.filter { it.token == token }

        if(loginUser.isNotEmpty()){
            UsersTable.setUserStatus(loginUser.last().login, newStatus)
            call.respond(SucsefullResponse(message = "Sucsefull!)))"))
        }
        else{
            call.respond(HttpStatusCode.BadRequest, "Вы не авторизованы в системе")
        }
    }
}