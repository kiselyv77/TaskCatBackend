package com.taskapp.features.addWorkspace

import com.taskapp.database.tables.tokens.TokensTable
import com.taskapp.database.stringTypes.UserTypes.CREATOR_TYPE
import com.taskapp.database.tables.usersToWorkSpaces.UserToWorkSpaceDAO
import com.taskapp.database.tables.usersToWorkSpaces.UserToWorkSpacesTable
import com.taskapp.database.tables.workspaces.WorkSpacesDAO
import com.taskapp.database.tables.workspaces.WorkSpacesTable
import com.taskapp.features.getWorkSpaces.WorkSpacesResponseDTO
import com.taskapp.utils.generateRandomUUID
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.utils.io.*

class AddWorkSpaceController(private val call: ApplicationCall) {
    suspend fun addWorkSpace(){
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
            call.respond(WorkSpacesResponseDTO(
                id = workSpace.id,
                name = workSpace.name,
                description = workSpace.description,
                creator = workSpace.creator,
                users = emptyList(),
                tasks = emptyList()
            ))
        }
        else{
            call.respond(HttpStatusCode.BadRequest, "Вы не авторизованны в системе!")
        }

    }
}