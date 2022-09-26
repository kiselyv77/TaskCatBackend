package com.taskapp.features.login

import kotlinx.serialization.Serializable


@Serializable
data class LoginResponseDTO(
    val token:String
)
