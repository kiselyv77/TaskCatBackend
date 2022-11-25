package com.taskapp.features.add.addNoteToTask

import kotlinx.serialization.Serializable


@Serializable
data class NoteDTO(
    val id: String,
    val info: String,
    val userId: String,
    val taskId: String,
    val attachmentFile: String
)
