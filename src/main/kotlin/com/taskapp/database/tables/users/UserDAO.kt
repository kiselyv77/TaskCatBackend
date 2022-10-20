package com.taskapp.database.tables.users

data class UserDAO(
    val name: String,
    val login: String,
    val status: String,
    val password: String,
)
