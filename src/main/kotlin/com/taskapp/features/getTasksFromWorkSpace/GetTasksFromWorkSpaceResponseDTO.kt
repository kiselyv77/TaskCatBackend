package com.taskapp.features.getTasksFromWorkSpace

import kotlinx.serialization.Serializable


@Serializable
data class GetTasksFromWorkSpaceResponseDTO(
    val id: String,
    val name:String,
    val description:String,
    val users:List<String>,
    val subTask:List<String>,
    val taskStatus:String
)
