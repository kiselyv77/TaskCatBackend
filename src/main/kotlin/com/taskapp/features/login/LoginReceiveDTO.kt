package com.taskapp.features.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginReceiveDTO(
    val login:String,
    val password:String
)
