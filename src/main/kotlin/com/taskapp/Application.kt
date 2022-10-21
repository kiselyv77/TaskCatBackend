package com.taskapp

import com.taskapp.features.addSubTaskToTask.configureAddSubTaskToTaskRouting
import com.taskapp.features.addTaskToWorkSpace.configureAddTaskToWorkSpaceRouting
import com.taskapp.features.addUserToTask.configureAddUserToTask
import com.taskapp.features.addUserToWorkSpace.configureAddUserToWorkSpaceRouting
import com.taskapp.features.addWorkspace.configureAddWorkSpaceRouting
import com.taskapp.features.getTasksFromWorkSpace.configureGetTasksFromWorkSpaceRouting
import com.taskapp.features.getUsers.configureGetUsersRouting
import com.taskapp.features.getWorkSpaces.configureGetWorkSpaceRouting
import com.taskapp.features.login.configureLoginRouting
import com.taskapp.features.register.configureRegisterRouting
import com.taskapp.features.setTaskValues.configureSetTaskValuesRouting
import com.taskapp.features.setUserValues.configureSetUserStatusRouting
import com.taskapp.features.workSpaceChat.configureSockets
import io.ktor.server.engine.*
import io.ktor.server.cio.*
import com.taskapp.plugins.*
import org.jetbrains.exposed.sql.Database

fun main() {
    embeddedServer(CIO, port = 8080, host = "localhost") {
        //подключение базы данных
        Database.connect(
            user = "postgres",
            url = "jdbc:postgresql://localhost:5432/taskappdb",
            password = "danil2002gimbarr1980kryt",
            driver = "org.postgresql.Driver"
        )
        //Конфигурирую тут все
        configureSerialization()
        // Регистрация и аунтификация
        configureRegisterRouting()
        configureLoginRouting()

        //workSpaces
        configureAddWorkSpaceRouting()
        configureAddTaskToWorkSpaceRouting()
        configureGetWorkSpaceRouting()
        configureAddUserToTask()
        configureAddUserToWorkSpaceRouting()

        // tasks
        configureGetTasksFromWorkSpaceRouting()
        configureAddSubTaskToTaskRouting()
        configureSetTaskValuesRouting()

        //users
        configureGetUsersRouting()
        configureSetUserStatusRouting()

        //webSockets
        configureSockets()

    }.start(wait = true)
}
