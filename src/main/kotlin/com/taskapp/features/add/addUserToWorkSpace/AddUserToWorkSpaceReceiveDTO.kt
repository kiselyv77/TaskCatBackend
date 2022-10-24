package com.taskapp.features.add.addUserToWorkSpace

import kotlinx.serialization.Serializable

@Serializable
data class AddUserToWorkSpaceReceiveDTO(
    val token: String,
    val invitedUserLogin: String,
    val workSpaceId: String
)
