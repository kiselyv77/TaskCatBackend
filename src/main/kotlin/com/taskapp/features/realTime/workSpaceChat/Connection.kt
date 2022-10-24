package com.taskapp.features.realTime.workSpaceChat

import com.taskapp.database.tables.mainTables.users.UserDAO
import io.ktor.websocket.*
import java.util.concurrent.atomic.AtomicInteger

class Connection(
    val session: DefaultWebSocketSession,
    val workSpaceId: String,
    userDAO: UserDAO,
    ) {
    companion object {
        val lastId = AtomicInteger(0)
    }
    val name = userDAO.name
    val login = userDAO.login
    val status = userDAO.status
}