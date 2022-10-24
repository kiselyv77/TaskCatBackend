package com.taskapp.features.auth.login

import kotlinx.serialization.Serializable


@Serializable
data class LoginResponseDTO(
    val token:String
)
