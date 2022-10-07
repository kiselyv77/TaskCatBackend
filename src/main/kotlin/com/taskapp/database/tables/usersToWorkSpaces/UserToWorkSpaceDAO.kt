package com.taskapp.database.tables.usersToWorkSpaces

import kotlinx.serialization.Serializable


data class UserToWorkSpaceDAO(
     val workSpacesId:String,
     val userLogin:String,
     val userStatusToWorkSpace:String
)
