package com.taskapp.features.setTaskValues

import com.taskapp.database.tables.tasks.TasksTable
import com.taskapp.database.tables.tokens.TokensTable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class SetTaskValuesController(val call: ApplicationCall) {
    suspend fun setTaskStatus() {
        val receive = call.receive<SetTaskValueReceiveDTO>()
        val tokens = TokensTable.getTokens()
        val loginUser = tokens.filter { it.token == receive.token }
        val task = TasksTable.getTaskById(receive.taskId)
        if(loginUser.isNotEmpty()){
            if(task!=null){
                TasksTable.setTaskStatus(
                    receive.newValue
                )
            }
            else{
                call.respond(HttpStatusCode.BadRequest, "Такой таски не существует")
            }
        }
        else{
            call.respond(HttpStatusCode.BadRequest, "Вы не авторизованы в системе")
        }

    }
    suspend fun setTaskName() {
        val receive = call.receive<SetTaskValueReceiveDTO>()
        val tokens = TokensTable.getTokens()
        val loginUser = tokens.filter { it.token == receive.token }
        val task = TasksTable.getTaskById(receive.taskId)
        if(loginUser.isNotEmpty()){
            if(task!=null){
                TasksTable.setTaskName(
                    receive.newValue
                )
            }
            else{
                call.respond(HttpStatusCode.BadRequest, "Такой таски не существует")
            }
        }
        else{
            call.respond(HttpStatusCode.BadRequest, "Вы не авторизованы в системе")
        }

    }
    suspend fun setTaskDescription() {
        val receive = call.receive<SetTaskValueReceiveDTO>()
        val tokens = TokensTable.getTokens()
        val loginUser = tokens.filter { it.token == receive.token }
        val task = TasksTable.getTaskById(receive.taskId)
        if(loginUser.isNotEmpty()){
            if(task!=null){
                TasksTable.setTaskDescription(
                    receive.newValue
                )
            }
            else{
                call.respond(HttpStatusCode.BadRequest, "Такой таски не существует")
            }
        }
        else{
            call.respond(HttpStatusCode.BadRequest, "Вы не авторизованы в системе")
        }

    }
}