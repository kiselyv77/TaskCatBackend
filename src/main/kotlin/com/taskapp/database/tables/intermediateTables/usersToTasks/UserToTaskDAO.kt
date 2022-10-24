package com.taskapp.database.tables.intermediateTables.usersToTasks

data class UserToTaskDAO(
    val userLogin:String,
    val taskId:String,
    val userStatusToTask:String
)
