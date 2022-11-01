package com.taskapp

import com.taskapp.features.add.addSubTaskToTask.configureAddSubTaskToTaskRouting
import com.taskapp.features.add.addTaskToWorkSpace.configureAddTaskToWorkSpaceRouting
import com.taskapp.features.add.addUserToTask.configureAddUserToTask
import com.taskapp.features.add.addUserToWorkSpace.configureAddUserToWorkSpaceRouting
import com.taskapp.features.add.addWorkspace.configureAddWorkSpaceRouting
import com.taskapp.features.get.getTasksFromWorkSpace.configureGetTasksFromWorkSpaceRouting
import com.taskapp.features.get.getUsers.configureGetUsersRouting
import com.taskapp.features.get.getWorkSpaces.configureGetWorkSpaceRouting
import com.taskapp.features.auth.login.configureLoginRouting
import com.taskapp.features.auth.register.configureRegisterRouting
import com.taskapp.features.files.configureGetAvatar
import com.taskapp.features.files.configureUploadNewAvatar
import com.taskapp.features.set.setTaskValues.configureSetTaskValuesRouting
import com.taskapp.features.set.setUserValues.configureSetUserStatusRouting
import com.taskapp.features.realTime.workSpaceChat.configureGetMessagesFromWorkSpace
import com.taskapp.features.realTime.workSpaceChat.configureSockets
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

        //messages
        configureGetMessagesFromWorkSpace()

        configureUploadNewAvatar()

        configureGetAvatar()

    }.start(wait = true)
}
