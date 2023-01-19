package com.taskapp.features.realTime.workSpaceChat

import com.taskapp.database.tables.mainTables.messages.MessageDAO
import com.taskapp.database.tables.mainTables.messages.MessagesTable
import com.taskapp.database.tables.mainTables.tokens.TokensTable
import com.taskapp.database.tables.mainTables.users.UsersTable
import com.taskapp.utils.getIsoDateTime
import io.ktor.serialization.kotlinx.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.server.websocket.WebSockets
import io.ktor.websocket.serialization.*
import kotlinx.serialization.json.Json
import java.nio.charset.Charset
import java.time.Duration
import java.util.*
import kotlin.collections.LinkedHashSet

fun Application.configureWorkSpaceChat() {
    routing {
        val connections = Collections.synchronizedSet<ConnectionChat?>(LinkedHashSet())
        webSocket("/chat/{token}/{workSpaceId}") {
            val token = call.parameters["token"] ?: ""
            val workSpaceId = call.parameters["workSpaceId"] ?: ""
            val tokens = TokensTable.getTokens()
            val loginUser = tokens.filter { it.token == token }
            val user = UsersTable.getUser(loginUser.last().login)
            val thisConnection = ConnectionChat(this, workSpaceId, user!!)
            connections += thisConnection
            try {
                println("the user connected by number ${connections.count()}")
                while (true) {
                    val messageDTO = receiveDeserialized<MessageDTO>()
                    println(messageDTO)
                    MessagesTable.insertMessage(
                        MessageDAO(
                            id = messageDTO.id,
                            timeStamp = messageDTO.timeStamp,
                            text = messageDTO.text,
                            sendingUser = messageDTO.sendingUser,
                            workSpaceId = messageDTO.workSpaceId,
                            type = messageDTO.type,
                            fileName = messageDTO.fileName
                        )
                    )

                    connections.forEach {
                        if(it.workSpaceId == messageDTO.workSpaceId){
                            it.session.sendSerializedBase(
                                messageDTO,
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