package com.taskapp.features.workSpaceChat

import kotlinx.serialization.Serializable

@Serializable
data class MessageReceiveDTO(
    val sendingUser: String,
    val workSpaceId: String,
    val text: String,
)