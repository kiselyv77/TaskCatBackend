package com.taskapp.features.register

import kotlinx.serialization.Serializable


@Serializable
data class RegisterResponseDTO(
    val token:String
)
