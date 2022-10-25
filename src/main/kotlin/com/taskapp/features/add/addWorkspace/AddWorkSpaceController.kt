package com.taskapp.features.add.addWorkspace

import com.taskapp.database.tables.mainTables.tokens.TokensTable
import com.taskapp.database.stringTypes.UserTypes.CREATOR_TYPE
import com.taskapp.database.tables.intermediateTables.usersToWorkSpaces.UserToWorkSpaceDAO
import com.taskapp.database.tables.intermediateTables.usersToWorkSpaces.UserToWorkSpacesTable
import com.taskapp.database.tables.mainTables.workspaces.WorkSpacesDAO
import com.taskapp.database.tables.mainTables.workspaces.WorkSpacesTable
import com.taskapp.features.get.getWorkSpaces.WorkSpacesResponseDTO
import com.taskapp.utils.generateRandomUUID
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class AddWorkSpaceController() {
    suspend fun addWorkSpace(call: ApplicationCall) {
        val receive = call.receive<AddWorkSpaceReceiveDTO>()
        val tokens = TokensTable.getTokens()
        val loginUser = tokens.filter { it.token == receive.token }
        val workSpaceId = generateRandomUUID()

        if(loginUser.isNotEmpty()){
            // Добавляем рабочее пространство
            val workSpace =  WorkSpacesDAO(
                id = workSpaceId,
                name = receive.name,
                description = receive.description,
                creator = loginUser.first().login,
            )
            WorkSpacesTable.insertWorkSpace(
                workSpace
            )
            // Добавляем креатора в юзеры которые работают в этом рабочем пространстве
            // Ставим ему статус креатора
            UserToWorkSpacesTable.insertUserToWorkSpace(
                UserToWorkSpaceDAO(
                    userLogin = loginUser.first().login,
                    workSpacesId = workSpaceId,
                    userStatusToWorkSpace = CREATOR_TYPE
                )
            )
            call.respond(
                WorkSpacesResponseDTO(
                id = workSpace.id,
                name = workSpace.name,
                description = workSpace.description,
                creator = workSpace.creator,
            )
            )
        }
        else{
            call.respond(HttpStatusCode.BadRequest, "Вы не авторизованны в системе!")
        }

    }
}