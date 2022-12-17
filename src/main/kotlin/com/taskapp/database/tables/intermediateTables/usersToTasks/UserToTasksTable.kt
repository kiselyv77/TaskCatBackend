package com.taskapp.database.tables.intermediateTables.usersToTasks

import com.taskapp.database.tables.intermediateTables.usersToWorkSpaces.UserToWorkSpacesTable
import com.taskapp.database.tables.mainTables.tasks.TasksTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object UserToTasksTable: Table() {
    private val userLogin = varchar("userLogin", 25)
    private val taskId = varchar("taskId", 50)
    private val userStatusToTask = varchar("userStatusToTask", 25)


    fun insertUserToTask(userToTask: UserToTaskDAO){
        transaction {
            insert {
                it[userLogin] = userToTask.userLogin
                it[taskId] = userToTask.taskId
                it[userStatusToTask] = userToTask.userStatusToTask
            }
        }
    }
    fun getUsersFromTask(taskId:String):List<UserToTaskDAO> {
        return try {
            transaction {
                UserToTasksTable.select{
                    UserToTasksTable.taskId eq taskId // Запрос в дазу банных
                }.toList().map {
                    UserToTaskDAO(
                        userLogin = it[userLogin],
                        taskId = it[UserToTasksTable.taskId],
                        userStatusToTask = it[userStatusToTask]
                    )
                }
            }
        }catch (e: Exception) {
            emptyList()
        }
    }

    fun deleteAllFromTask(taskId: String) {
        transaction {
            deleteWhere {
                UserToTasksTable.taskId eq taskId
            }
        }
    }

    fun deleteUserFromTask(taskId: String, userLogin: String) {
        transaction {
            deleteWhere {
                UserToTasksTable.userLogin eq userLogin and(UserToTasksTable.taskId eq taskId)
            }
        }
    }
}