package com.taskapp.utils

fun getRootPackage(): String{
    val isRelease = true
    return if(isRelease) "root/serverFiles"
    else "C:\\Users\\Mi\\Desktop\\serverFiles"
}

fun getFileSeparator(): String{
    val isRelease = true
    return if(isRelease) "/" // for linux
    else "\\" // for windows
}