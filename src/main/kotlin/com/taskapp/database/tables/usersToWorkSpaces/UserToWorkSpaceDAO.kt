package com.taskapp.database.tables.usersToWorkSpaces

data class UserToWorkSpaceDAO(
     val workSpacesId:String,
     val userLogin:String,
     val userStatusToWorkSpace:String
)
