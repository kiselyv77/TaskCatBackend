package com.taskapp.features.set.setUserValues

import com.taskapp.database.stringTypes.UserStatus
import com.taskapp.database.tables.intermediateTables.usersToWorkSpaces.UserToWorkSpacesTable
import com.taskapp.database.tables.mainTables.tokens.TokensTable
import com.taskapp.database.tables.mainTables.users.UsersTable
import com.taskapp.database.tables.mainTables.workspaces.WorkSpacesTable
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

    suspend fun  setUserStatusToWorkSpace(call:ApplicationCall){
        val token = call.parameters["token"] ?: ""
        val userLogin = call.parameters["userLogin"] ?: ""
        val newStatus = call.parameters["newStatus"] ?: UserStatus.OFFLINE_STATUS
        val workSpaceId = call.parameters["workSpaceId"] ?: ""

        val tokens = TokensTable.getTokens()
        val loginUser = tokens.filter { it.token == token }
        val workSpace = WorkSpacesTable.getWorkSpaceById(workSpaceId) ?: return

        if(loginUser.isNotEmpty()){
            if(workSpace.creator == loginUser.last().login){
                UserToWorkSpacesTable.setUserStatusToWorkSpace(
                    loginUser = userLogin,
                    workSpaceId = workSpaceId,
                    newStatus = newStatus
                )
                call.respond(SucsefullResponse("Sucsefull!!!"))
            }
            else{
                call.respond(HttpStatusCode.BadRequest, "Поменять статус может только создатель")
            }
        }
        else{
            call.respond(HttpStatusCode.BadRequest, "Вы не авторизованы в системе")
        }

    }
}