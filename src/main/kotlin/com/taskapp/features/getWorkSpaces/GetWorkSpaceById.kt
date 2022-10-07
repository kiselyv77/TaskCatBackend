package com.taskapp.features.getWorkSpaces

import ch.qos.logback.core.subst.Token
import kotlinx.serialization.Serializable

@Serializable
data class GetWorkSpaceById(
    val token: String,
    val workSpaceId: String
)
