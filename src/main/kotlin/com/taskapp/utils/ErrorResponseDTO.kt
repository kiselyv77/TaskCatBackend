package com.taskapp.utils

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponseDTO(
    val error: String
)
