package com.taskapp.database.tables.usersToWorkSpaces

import com.taskapp.database.stringTypes.UserTypes.MEMBER_TYPE
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object UserToWorkSpacesTable:Table() {
    private val workSpacesId= varchar("workSpacesId", 50)
    private val userLogin = varchar("userLogin", 50)

    fun insertUserToWorkSpace(userToWorkSpaceDAO: UserToWorkSpaceDAO){
        transaction {
            insert{
                it[workSpacesId] = userToWorkSpaceDAO.workSpacesId
                it[userLogin] = userToWorkSpaceDAO.userLogin
            }
        }

    }
    fun getUserFromWorkSpace(workId:String): List<UserToWorkSpaceDAO> {
        return try {
            transaction {
                UserToWorkSpacesTable.selectAll().toList().map {
                    UserToWorkSpaceDAO(
                        workSpacesId = it[workSpacesId],
                        userLogin = it[userLogin],
                        userStatusToWorkSpace = MEMBER_TYPE
                    )
                }.filter { it.workSpacesId == workId }
            }
        }catch (e: Exception) {
            emptyList()
        }
    }
}