package com.taskapp.features.realTime.taskNotes

import com.taskapp.database.tables.mainTables.messages.MessageDAO
import com.taskapp.database.tables.mainTables.messages.MessagesTable
import com.taskapp.database.tables.mainTables.notes.NoteDAO
import com.taskapp.database.tables.mainTables.notes.NotesTable
import com.taskapp.database.tables.mainTables.tokens.TokensTable
import com.taskapp.database.tables.mainTables.users.UsersTable
import com.taskapp.features.realTime.workSpaceChat.ConnectionChat
import com.taskapp.features.realTime.workSpaceChat.MessageDTO
import io.ktor.serialization.kotlinx.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.serialization.*
import kotlinx.serialization.json.Json
import java.nio.charset.Charset
import java.time.Duration
import java.util.*
import kotlin.collections.LinkedHashSet

fun Application.configureNotes() {

    routing {
        val connections = Collections.synchronizedSet<ConnectionNotes?>(LinkedHashSet())
        webSocket("/notes/{token}/{taskId}") {
            val token = call.parameters["token"] ?: ""
            val taskId = call.parameters["taskId"] ?: ""
            val tokens = TokensTable.getTokens()
            val loginUser = tokens.filter { it.token == token }
            val user = UsersTable.getUser(loginUser.last().login)

            val thisConnection = ConnectionNotes(this, taskId, user!!)
            connections += thisConnection
            try {
                println("the user connected by number ${connections.count()}")
                while (true) {
                    val noteDTO = receiveDeserialized<NoteDTO>()
                    println(noteDTO)
                    NotesTable.addNote(
                        NoteDAO(
                            id = noteDTO.id,
                            info = noteDTO.info,
                            loginUser = noteDTO.loginUser,
                            taskId = noteDTO.taskId,
                            attachmentFile = noteDTO.attachmentFile,
                            dateTime = noteDTO.dateTime
                        )
                    )
                    connections.forEach {
                        if(it.taskId == noteDTO.taskId){
                            it.session.sendSerializedBase(
                                noteDTO,
                                KotlinxWebsocketSerializationConverter(Json),
                                Charset.defaultCharset()
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                println(e.localizedMessage)
            } finally {
                println("Removing $thisConnection!")
                connections -= thisConnection
            }
        }
    }
}