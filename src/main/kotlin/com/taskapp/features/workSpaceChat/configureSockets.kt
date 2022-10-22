package com.taskapp.features.workSpaceChat

import com.taskapp.database.tables.tokens.TokensTable
import com.taskapp.database.tables.users.UsersTable
import io.ktor.serialization.kotlinx.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import io.ktor.websocket.serialization.*
import kotlinx.serialization.json.Json
import java.nio.charset.Charset
import java.time.Duration
import java.util.*
import kotlin.collections.LinkedHashSet

fun Application.configureSockets() {
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }
    routing {
        val connections = Collections.synchronizedSet<Connection?>(LinkedHashSet())
        webSocket("/chat/{token}") {
            val token = call.parameters["token"]
            val tokens = TokensTable.getTokens()
            val loginUser = tokens.filter { it.token ==token }
            val user = UsersTable.getUser(loginUser.last().login)
            val thisConnection = Connection(this, user!!)
            connections += thisConnection
            try {
                println("the user connected by number ${connections.count()}")
                for (frame in incoming) {
                    frame as? Frame.Text ?: continue
                    val receivedText = frame.readText()
                    val textWithUsername = MessageResponseDTO(
                        userName = thisConnection.name,
                        userLogin = thisConnection.login,
                        text = receivedText
                    )
                    connections.forEach {
                        it.session.sendSerializedBase(
                            textWithUsername,
                            KotlinxWebsocketSerializationConverter(Json),
                            Charset.defaultCharset()
                        )
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