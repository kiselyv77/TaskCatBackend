package com.taskapp.database.tables.intermediateTables.usersToWorkSpaces

import com.taskapp.database.stringTypes.UserTypes.MEMBER_TYPE
import com.taskapp.database.tables.mainTables.messages.MessagesTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object UserToWorkSpacesTable : Table() {
    private val workSpacesId = varchar("workSpacesId", 50)
    private val userLogin = varchar("userLogin", 50)
    private val userStatusToWorkSpace = varchar("userStatusToWorkSpace", 25)


    fun insertUserToWorkSpace(userToWorkSpaceDAO: UserToWorkSpaceDAO) {
        transaction {
            insert {
                it[workSpacesId] = userToWorkSpaceDAO.workSpacesId
                it[userLogin] = userToWorkSpaceDAO.userLogin
                it[userStatusToWorkSpace] = userToWorkSpaceDAO.userStatusToWorkSpace
            }
        }

    }

    fun getUserFromWorkSpace(workId: String): List<UserToWorkSpaceDAO> {
        return try {
            transaction {
                UserToWorkSpacesTable.selectAll().toList().map {
                    UserToWorkSpaceDAO(
                        workSpacesId = it[workSpacesId],
                        userLogin = it[userLogin],
                        userStatusToWorkSpace = it[userStatusToWorkSpace]
                    )
                }.filter { it.workSpacesId == workId }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun getWorkSpacesForUser(loginUser: String): List<UserToWorkSpaceDAO> {
        return try {
            transaction {
                UserToWorkSpacesTable.selectAll().toList().map {
                    UserToWorkSpaceDAO(
                        workSpacesId = it[workSpacesId],
                        userLogin = it[userLogin],
                        userStatusToWorkSpace = MEMBER_TYPE
                    )
                }.filter { it.userLogin == loginUser }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun setUserStatusToWorkSpace(loginUser: String, workSpaceId: String, newStatus: String) {
        transaction {
            update({
                UserToWorkSpacesTable.userLogin eq loginUser and(UserToWorkSpacesTable.workSpacesId eq workSpaceId)
            }) {
                it[UserToWorkSpacesTable.userStatusToWorkSpace] = newStatus
            }
        }
    }

    fun deleteAllFromWorkSpace(workSpaceId: String) {
        transaction {
            deleteWhere {
                UserToWorkSpacesTable.workSpacesId eq workSpaceId
            }
        }
    }
}