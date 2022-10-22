package com.taskapp.features.workSpaceChat

import kotlinx.serialization.Serializable


@Serializable
data class MessageResponseDTO(
    val userName: String,
    val userLogin: String,
    val text: String
)
