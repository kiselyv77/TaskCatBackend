package com.taskapp.features.addTaskToWorkSpace

import kotlinx.serialization.Serializable


@Serializable
data class AddTaskToWorkSpaceReceiveDTO(
    val token: String,
    val name: String,
    val description: String,
    val workSpaceId: String
)
