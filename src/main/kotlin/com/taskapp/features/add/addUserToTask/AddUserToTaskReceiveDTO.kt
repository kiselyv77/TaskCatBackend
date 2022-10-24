package com.taskapp.features.add.addUserToTask

import kotlinx.serialization.Serializable


@Serializable
data class AddUserToTaskReceiveDTO(
    val token:String,
    val userLogin:String,
    val taskId:String
)