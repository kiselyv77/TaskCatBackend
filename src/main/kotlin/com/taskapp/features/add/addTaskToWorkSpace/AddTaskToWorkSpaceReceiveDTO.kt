package com.taskapp.features.add.addTaskToWorkSpace

import kotlinx.serialization.Serializable

@Serializable
data class AddTaskToWorkSpaceReceiveDTO(
    val token: String,
    val name: String,
    val description: String,
    val workSpaceId: String,
    val userList: List<String>,
    val deadLine: String
)

