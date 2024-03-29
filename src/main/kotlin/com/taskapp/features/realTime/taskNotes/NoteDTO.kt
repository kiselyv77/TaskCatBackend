package com.taskapp.features.realTime.taskNotes

import kotlinx.serialization.Serializable


@Serializable
data class NoteDTO(
    val id: String,
    val info: String,
    val loginUser: String,
    val userName: String,
    val taskId: String,
    val attachmentFile: String,
    val timeStamp: String
)
