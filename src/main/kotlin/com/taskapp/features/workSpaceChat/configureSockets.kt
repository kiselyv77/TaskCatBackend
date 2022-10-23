package com.taskapp.features.workSpaceChat

import com.taskapp.database.tables.messages.MessageDAO
import com.taskapp.database.tables.messages.MessagesTable
import com.taskapp.database.tables.tokens.TokensTable
import com.taskapp.database.tables.users.UsersTable
import com.taskapp.utils.generateRandomUUID
import io.ktor.serialization.kotlinx.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.server.websocket.WebSockets
import io.ktor.websocket.serialization.*
import kotlinx.serialization.json.Json
import java.nio.charset.Charset
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.LinkedHashSet

fun Application.configureSockets() {
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
        contentConverter = KotlinxWebsocketSerializationConverter(Json)
    }
    routing {
        val connections = Collections.synchronizedSet<Connection?>(LinkedHashSet())
        webSocket("/chat/{token}/{workSpaceId}") {
            val token = call.parameters["token"] ?: ""
            val workSpaceId = call.parameters["workSpaceId"] ?: ""
            val tokens = TokensTable.getTokens()
            val loginUser = tokens.filter { it.token == token }
            val user = UsersTable.getUser(loginUser.last().login)
            val thisConnection = Connection(this, workSpaceId, user!!)
            connections += thisConnection
            try {
                println("the user connected by number ${connections.count()}")
                while (true) {
                    val messageReceive = receiveDeserialized<MessageReceiveDTO>()
                    val messageId = generateRandomUUID()
                    val messageDateTime = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
                    MessagesTable.insertMessage(
                        MessageDAO(
                            id = messageId,
                            dateTime = messageDateTime,
                            text = messageReceive.text,
                            sendingUser = messageReceive.sendingUser,
                            workSpaceId = messageReceive.workSpaceId,
                        )
                    )
                    val messageResponse = MessageResponseDTO(
                        id = messageId,
                        userName = user.name,
                        sendingUser = messageReceive.sendingUser,
                        workSpaceId = messageReceive.workSpaceId,
                        dateTime = messageDateTime,
                        text = messageReceive.text
                    )

                    connections.forEach {
                        if(it.workSpaceId == messageResponse.workSpaceId){
                            it.session.sendSerializedBase(
                                messageResponse,
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