package com.taskapp.features.realTime.taskNotes

import com.taskapp.database.tables.mainTables.users.UserDAO
import io.ktor.websocket.*
import java.util.concurrent.atomic.AtomicInteger

class ConnectionNotes(
    val session: DefaultWebSocketSession,
    val taskId: String,
    userDAO: UserDAO,
) {
    companion object {
        val lastId = AtomicInteger(0)
    }

    val name = userDAO.name
    val login = userDAO.login
    val status = userDAO.status
}