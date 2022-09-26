package com.taskapp.database.tables.subTasksToTask

import com.taskapp.database.tables.tasksToWorkSpaces.TaskToWorkSpaceDAO
import com.taskapp.database.tables.tasksToWorkSpaces.TaskToWorkSpacesTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object SubTaskToTasksTable: Table() {
    private val taskId = varchar("taskId", 50)
    private val subTaskId = varchar("subTaskId", 50)

    fun addSubTaskToTask(subTaskToTaskDAO: SubTaskToTaskDAO){
        transaction {
            insert{
                it[taskId] = subTaskToTaskDAO.taskId
                it[subTaskId] = subTaskToTaskDAO.subTaskId
            }
        }
    }

    fun getSubTasksToTask(taskId:String):List<SubTaskToTaskDAO>{
        return try {
            transaction {
                SubTaskToTasksTable.select{
                    SubTaskToTasksTable.taskId eq taskId // Запрос в дазу банных
                }.toList().map {
                    SubTaskToTaskDAO(
                        taskId = it[SubTaskToTasksTable.taskId],
                        subTaskId = it[SubTaskToTasksTable.subTaskId]
                    )
                }
            }
        }catch (e: Exception) {
            emptyList()
        }
    }
}