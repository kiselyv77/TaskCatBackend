package com.taskapp.features.get.getWorkSpaces

import kotlinx.serialization.Serializable


@Serializable
data class WorkSpacesResponseDTO(
    val id:String,
    val name:String,
    val description:String,
    val creator:String,
)