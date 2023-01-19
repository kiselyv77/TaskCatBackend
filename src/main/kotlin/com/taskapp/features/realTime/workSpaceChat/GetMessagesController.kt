package com.taskapp.features.realTime.workSpaceChat

import com.taskapp.database.tables.mainTables.messages.MessagesTable
import com.taskapp.database.tables.mainTables.tokens.TokensTable
import com.taskapp.database.tables.mainTables.users.UsersTable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

class GetMessagesController() {

    suspend fun getMessagesFromWorkSpace(call: ApplicationCall) {
        val token = call.parameters["token"]
        val workSpaceId = call.parameters["workSpaceId"] ?: ""

        val offset = call.parameters["offset"]?.toInt()?: 0

        val tokens = TokensTable.getTokens()
        val loginUser = tokens.filter { it.token == token }

        println(token)
        println(workSpaceId)
        println(offset)

        if(loginUser.isNotEmpty()){
            val messages = MessagesTable.getMessagesFromWorkSpace(workSpaceId, offset)
            val messagesResponseDTO = messages.map{
                val user = UsersTable.getUser(it.sendingUser)
                MessageDTO(
                    id = it.id,
                    userName = user?.name.toString(),
                    sendingUser =  it.sendingUser,
                    workSpaceId = it.workSpaceId,
                    type = it.type,
                    timeStamp = it.timeStamp,
                    text = it.text,
                    fileName = it.fileName
                )
            }
            println(messages)
            call.respond(messagesResponseDTO)
        }
        else{
            call.respond(HttpStatusCode.BadRequest, "Вы не авторизованны в системе!")
        }
    }
}