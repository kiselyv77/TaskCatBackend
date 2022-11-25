package com.taskapp.database.tables.mainTables.notes

data class NoteDAO(
    val id: String,
    val info: String,
    val loginUser: String,
    val taskId: String,
    val attachmentFile: String,
    val dateTime: String
)
