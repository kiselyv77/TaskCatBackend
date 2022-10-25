package com.taskapp.features.get.getTasksFromWorkSpace

import kotlinx.serialization.Serializable


@Serializable
data class GetTasksFromWorkSpaceResponseDTO(
    val id: String,
    val name:String,
    val description:String,
    val taskStatus:String
)
