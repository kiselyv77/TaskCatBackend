package com.taskapp.features.auth.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginReceiveDTO(
    val login:String,
    val password:String
)
