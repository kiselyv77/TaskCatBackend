package com.taskapp

import com.taskapp.features.add.addTaskToWorkSpace.configureAddTaskToWorkSpaceRouting
import com.taskapp.features.add.addUserToTask.configureAddUserToTask
import com.taskapp.features.add.addUserToWorkSpace.configureAddUserToWorkSpaceRouting
import com.taskapp.features.add.addWorkspace.configureAddWorkSpaceRouting
import com.taskapp.features.auth.login.configureLoginRouting
import com.taskapp.features.auth.register.configureRegisterRouting
import com.taskapp.features.delete.deleteTask.configureDeleteTask
import com.taskapp.features.delete.deleteUserFromTask.configureDeleteUserFromTask
import com.taskapp.features.delete.deleteUserFromWorkSpace.configureDeleteUserFromWorkSpace
import com.taskapp.features.delete.deleteWorkSpace.configureDeleteWorkSpace
import com.taskapp.features.files.*
import com.taskapp.features.get.getTasksFromWorkSpace.configureGetTasksFromWorkSpaceRouting
import com.taskapp.features.get.getUsers.configureGetUsersRouting
import com.taskapp.features.get.getWorkSpaces.configureGetWorkSpaceRouting
import com.taskapp.features.realTime.configureWebSockets
import com.taskapp.features.realTime.taskNotes.configureGetNotesFromTask
import com.taskapp.features.realTime.taskNotes.configureNotes
import com.taskapp.features.realTime.workSpaceChat.configureGetMessagesFromWorkSpace
import com.taskapp.features.realTime.workSpaceChat.configureWorkSpaceChat
import com.taskapp.features.set.setTaskValues.configureSetTaskValuesRouting
import com.taskapp.features.set.setUserValues.configureSetUserStatusRouting
import com.taskapp.plugins.configureSerialization
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
        //подключение базы данных
        Database.connect(
            user = Secrets.dbUserName,
            url = Secrets.dbUri,
            password = Secrets.dbPassword,
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
        configureDeleteWorkSpace()

        // tasks
        configureGetTasksFromWorkSpaceRouting()
        configureSetTaskValuesRouting()
        configureDeleteTask()

        //users
        configureGetUsersRouting()
        configureSetUserStatusRouting()
        configureDeleteUserFromTask()
        configureDeleteUserFromWorkSpace()

        //webSockets

        configureWebSockets()


        //messages
        configureWorkSpaceChat()
        configureGetMessagesFromWorkSpace()

        configureUploadNewAvatar()

        configureGetAvatar()

        configureGetVoiceMessage()

        configureUploadVoiceMessage()

        //notes

        configureNotes()

        configureGetNotesFromTask()

        configureUploadNoteAttachmentFile()

        configureGetNoteAttachmentFile()
}
