package com.taskapp.features.auth.register

import kotlinx.serialization.Serializable


@Serializable
data class RegisterReceiveDTO(
    val name:String,
    val login:String,
    val password:String
)
