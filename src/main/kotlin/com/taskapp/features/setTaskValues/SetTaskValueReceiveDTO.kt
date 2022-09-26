package com.taskapp.features.setTaskValues

import kotlinx.serialization.Serializable


@Serializable
data class SetTaskValueReceiveDTO(
    val token:String,
    val taskId:String,
    val newValue:String
)

