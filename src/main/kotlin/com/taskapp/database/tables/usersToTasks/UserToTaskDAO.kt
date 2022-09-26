package com.taskapp.database.tables.usersToTasks

data class UserToTaskDAO(
    val userLogin:String,
    val taskId:String,
    val userStatusToTask:String
)
