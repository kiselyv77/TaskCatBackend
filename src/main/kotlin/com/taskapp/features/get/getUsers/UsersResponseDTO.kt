package com.taskapp.features.get.getUsers

import kotlinx.serialization.Serializable

@Serializable
data class UsersResponseDTO(
    val name:String,
    val status: String,
    val login:String,
    val userStatusToWorkSpace: String = "",
    val userStatusToTask: String = ""
)
