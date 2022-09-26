package com.taskapp.database.tables.subTasks

data class SubTaskDAO(
    val id: String,
    val name:String,
    val description:String,
    val status:String
)