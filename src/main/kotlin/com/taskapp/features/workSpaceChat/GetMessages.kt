package com.taskapp.features.workSpaceChat

import com.taskapp.database.tables.messages.MessagesTable
import com.taskapp.database.tables.tasks.TasksTable
import com.taskapp.database.tables.tasksToWorkSpaces.TaskToWorkSpacesTable
import com.taskapp.database.tables.tokens.TokensTable
import com.taskapp.database.tables.users.UsersTable
import com.taskapp.features.getTasksFromWorkSpace.GetTasksFromWorkSpaceResponseDTO
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class GetMessages(val call: ApplicationCall) {

    suspend fun getMessagesFromWorkSpace(){
        val token = call.parameters["token"]
        val workSpaceId = call.parameters["workSpaceId"] ?: ""

        val tokens = TokensTable.getTokens()
        val loginUser = tokens.filter { it.token == token }

        if(loginUser.isNotEmpty()){
            val messages = MessagesTable.getMessagesFromWorkSpace(workSpaceId)
            val messagesResponseDTO = messages.map{
                val user = UsersTable.getUser(loginUser.last().login)
                MessageResponseDTO(
                    id = it.id,
                    userName = user?.name.toString(),
                    sendingUser =  it.sendingUser,
                    workSpaceId = it.workSpaceId,
                    dateTime = it.dateTime,
                    text = it.text
                )
            }
            println(messagesResponseDTO)
            val cmp = compareBy<MessageResponseDTO> { LocalDateTime.parse(it.dateTime, DateTimeFormatter.ISO_DATE_TIME) }
            call.respond(messagesResponseDTO.sortedWith(cmp).reversed())
        }
        else{
            println("чего бля?")
            call.respond(HttpStatusCode.BadRequest, "Вы не авторизованны в системе!")
        }
    }
}