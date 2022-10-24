package com.taskapp.features.add.addSubTaskToTask

import kotlinx.serialization.Serializable


@Serializable
data class AddSubTaskToTaskReceiveDTO(
    val token:String,
    val name:String,
    val description:String,
    val taskId:String,
)
