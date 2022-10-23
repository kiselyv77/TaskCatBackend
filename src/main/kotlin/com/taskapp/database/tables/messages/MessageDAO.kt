package com.taskapp.database.tables.messages

import com.taskapp.features.getWorkSpaces.GetWorkSpaceById

data class MessageDAO(
    val id: String,
    val dateTime: String,
    val text: String,
    val sendingUser: String,
    val workSpaceId: String
)