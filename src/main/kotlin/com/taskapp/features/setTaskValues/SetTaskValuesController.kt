package com.taskapp.features.setTaskValues

import com.taskapp.database.stringTypes.TaskStatus
import com.taskapp.database.tables.tasks.TasksTable
import com.taskapp.database.tables.tokens.TokensTable
import com.taskapp.utils.SucsefullResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class SetTaskValuesController(val call: ApplicationCall) {
    suspend fun setTaskStatus() {
        val token = call.parameters["token"]
        val taskId = call.parameters["taskId"]?:""
        val newValue = call.parameters["newStatus"]?:TaskStatus.INPROGRESS_TYPE
        val tokens = TokensTable.getTokens()
        val loginUser = tokens.filter { it.token == token }
        val task = TasksTable.getTaskById(taskId)
        if(loginUser.isNotEmpty()){
            if(task!=null){
                TasksTable.setTaskStatus(
                    taskId,
                    newValue
                )
                call.respond(SucsefullResponse(message = "Sucsefull!)))"))
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
        val token = call.parameters["token"]
        val taskId = call.parameters["taskId"]?:""
        val newValue = call.parameters["newValue"]?:""
        val tokens = TokensTable.getTokens()
        val loginUser = tokens.filter { it.token == token }
        val task = TasksTable.getTaskById(taskId)
        if(loginUser.isNotEmpty()){
            if(task!=null){
                TasksTable.setTaskName(
                    newValue
                )
                call.respond("SUCSEFULL")
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
        val token = call.parameters["token"]
        val taskId = call.parameters["taskId"]?:""
        val newValue = call.parameters["newValue"]?:""
        val tokens = TokensTable.getTokens()
        val loginUser = tokens.filter { it.token == token }
        val task = TasksTable.getTaskById(taskId)
        if(loginUser.isNotEmpty()){
            if(task!=null){
                TasksTable.setTaskDescription(
                    newValue
                )
                call.respond("SUCSEFULL")
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