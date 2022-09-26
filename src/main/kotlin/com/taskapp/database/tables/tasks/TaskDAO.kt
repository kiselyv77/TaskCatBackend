package com.taskapp.database.tables.tasks

data class TaskDAO(
    val id: String,
    val name:String,
    val description:String,
    val status:String
)
