package com.taskapp.features.add.addNoteToTask

import com.taskapp.database.tables.mainTables.notes.NoteDAO
import com.taskapp.database.tables.mainTables.notes.NotesTable
import com.taskapp.database.tables.mainTables.tokens.TokensTable
import com.taskapp.utils.generateRandomUUID
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

class AddNoteToTaskController() {
    suspend fun addNoteToTask(call: ApplicationCall) {

        val token = call.parameters["token"]
        val info = call.parameters["info"] ?: ""
        val taskId = call.parameters["taskId"]  ?: ""

        val newNoteId = generateRandomUUID()
        val tokens = TokensTable.getTokens()
        val loginUser = tokens.filter { it.token == token } // по токену определяем логин
        if (loginUser.isNotEmpty()) {
            val noteDAo = NoteDAO(
                id = newNoteId,
                info = info,
                loginUser = loginUser.last().login,
                taskId = taskId,
                attachmentFile = "",
                dateTime = ""
            )
            NotesTable.addNote(noteDAo)
        }
        else{
            call.respond(HttpStatusCode.BadRequest, "Вы не авторизованны в системе")
        }
    }
}