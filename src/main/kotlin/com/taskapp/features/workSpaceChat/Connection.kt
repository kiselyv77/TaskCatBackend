package com.taskapp.features.workSpaceChat

import com.taskapp.database.tables.users.UserDAO
import io.ktor.websocket.*
import java.util.concurrent.atomic.AtomicInteger

class Connection(val session: DefaultWebSocketSession, userDAO: UserDAO) {
    companion object {
        val lastId = AtomicInteger(0)
    }
    val name = userDAO.name
    val login = userDAO.login
    val status = userDAO.status
}