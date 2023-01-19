package com.taskapp.database.tables.mainTables.messages


data class MessageDAO(
    val id: String,
    val timeStamp: String,
    val text: String,
    val type:String,
    val sendingUser: String,
    val workSpaceId: String,
    val fileName: String
)