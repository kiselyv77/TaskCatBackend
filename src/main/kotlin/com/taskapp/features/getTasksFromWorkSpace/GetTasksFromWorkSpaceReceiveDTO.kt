package com.taskapp.features.getTasksFromWorkSpace

import kotlinx.serialization.Serializable

@Serializable
data class GetTasksFromWorkSpaceReceiveDTO(
    val workSpaceId: String
)
