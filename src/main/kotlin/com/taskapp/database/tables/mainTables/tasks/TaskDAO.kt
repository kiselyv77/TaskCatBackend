package com.taskapp.database.tables.mainTables.tasks

data class TaskDAO(
    val id: String,
    val workSpaceId:String,
    val name: String,
    val description: String,
    val status: String,
    val deadLine: String,
    val creationDate: String
)
