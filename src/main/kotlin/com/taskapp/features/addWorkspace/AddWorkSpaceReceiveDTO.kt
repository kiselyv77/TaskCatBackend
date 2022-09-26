package com.taskapp.features.addWorkspace

import kotlinx.serialization.Serializable


@Serializable
data class AddWorkSpaceReceiveDTO(
    val token:String,
    val name:String,
    val description: String,
)
