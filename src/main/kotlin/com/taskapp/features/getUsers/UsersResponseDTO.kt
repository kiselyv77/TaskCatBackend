package com.taskapp.features.getUsers

import kotlinx.serialization.Serializable

@Serializable
data class UsersResponseDTO(
    val name:String,
    val login:String,
)
