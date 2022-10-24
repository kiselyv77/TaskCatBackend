package com.taskapp.features.auth.register

import kotlinx.serialization.Serializable


@Serializable
data class RegisterResponseDTO(
    val token:String
)
