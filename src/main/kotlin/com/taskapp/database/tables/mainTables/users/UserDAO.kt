package com.taskapp.database.tables.mainTables.users

data class UserDAO(
    val name: String,
    val login: String,
    val status: String,
    val password: String,
)
