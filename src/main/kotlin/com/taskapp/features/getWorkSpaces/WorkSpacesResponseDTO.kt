package com.taskapp.features.getWorkSpaces

import kotlinx.serialization.Serializable


@Serializable
data class WorkSpacesResponseDTO(
    val id:String,
    val name:String,
    val description:String,
    val creator:String,
    val users: List<String> = emptyList(),
    val tasks: List<String> = emptyList()
)