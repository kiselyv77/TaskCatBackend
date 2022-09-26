package com.taskapp.database.tables.tasksToWorkSpaces

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object TaskToWorkSpacesTable : Table() {
    private val taskId = varchar("taskId", 50)
    private val workSpaceId = varchar("workSpaceId", 50)

    fun insertTaskToWorkSpace(taskToWorkSpaceDAO: TaskToWorkSpaceDAO) {
        transaction {
            insert {
                it[taskId] = taskToWorkSpaceDAO.taskId
                it[workSpaceId] = taskToWorkSpaceDAO.workSpaceId
            }
        }
    }
    fun getTasksFromWorkSpace(workSpaceId:String):List<TaskToWorkSpaceDAO> {
        return try {
            transaction {
                TaskToWorkSpacesTable.select{
                    TaskToWorkSpacesTable.workSpaceId eq workSpaceId // Запрос в дазу банных
                }.toList().map {
                    TaskToWorkSpaceDAO(
                        taskId = it[taskId],
                        workSpaceId = it[TaskToWorkSpacesTable.workSpaceId]
                    )
                }
            }
        }catch (e: Exception) {
            emptyList()
        }
    }

}