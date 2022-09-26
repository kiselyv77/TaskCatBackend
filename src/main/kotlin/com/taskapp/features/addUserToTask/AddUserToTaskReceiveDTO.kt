package com.taskapp.features.addUserToTask

import kotlinx.serialization.Serializable


@Serializable
data class AddUserToTaskReceiveDTO(
    val token:String,
    val userLogin:String,
    val taskId:String
)
