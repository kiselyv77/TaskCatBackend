package com.taskapp.features.realTime.taskNotes

import com.taskapp.database.tables.mainTables.notes.NotesTable
import com.taskapp.database.tables.mainTables.tokens.TokensTable
import com.taskapp.database.tables.mainTables.users.UsersTable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

class GetNotesFromTaskController {
    suspend fun getNotes(call: ApplicationCall) {
        val token = call.parameters["token"]
        val taskId = call.parameters["taskId"] ?: ""

        val offset = call.parameters["offset"]?.toInt()?: 0

        val tokens = TokensTable.getTokens()
        val loginUser = tokens.filter { it.token == token }

        if(loginUser.isNotEmpty()){
            val notes = NotesTable.getNotes(taskId, offset)
            val notesResponseDTO = notes.map{
                NoteDTO(
                    id = it.id,
                    info = it.info,
                    loginUser = it.loginUser,
                    userName = it.userName,
                    taskId = it.taskId,
                    attachmentFile = it.attachmentFile,
                    timeStamp = it.timeStamp
                )
            }
            println(notes)
            call.respond(notesResponseDTO)
        }
        else{
            call.respond(HttpStatusCode.BadRequest, "Вы не авторизованны в системе!")
        }
    }

}