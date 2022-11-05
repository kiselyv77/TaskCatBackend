package com.taskapp.features.realTime.workSpaceChat

import kotlinx.serialization.Serializable


@Serializable
data class MessageDTO(
    val id:String,
    val userName: String,
    val sendingUser: String,
    val workSpaceId: String,
    val type: String,
    val dateTime:String,
    val text: String
)
