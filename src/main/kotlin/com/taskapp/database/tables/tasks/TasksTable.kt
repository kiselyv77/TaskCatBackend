package com.taskapp.database.tables.tasks

import com.taskapp.database.stringTypes.TaskStatus
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.logging.Filter

object TasksTable : Table() {
    private val id = varchar("id", 50,)
    private val name = varchar("name", 25)
    private val description = varchar("description", 1000)
    private val status = varchar("status", 25)

    fun insertTable(taskDAO: TaskDAO) {
        transaction {
            insert {
                it[id] = taskDAO.id
                it[name] = taskDAO.name
                it[description] = taskDAO.description
                it[status] = taskDAO.status
            }
        }
    }

    fun getTaskById(id: String): TaskDAO? {
        return try {
            transaction {
                val userResultRow = TasksTable.select { TasksTable.id.eq(id)}.single()
                TaskDAO(
                    id = userResultRow[TasksTable.id],
                    name = userResultRow[name],
                    description = userResultRow[description],
                    status = userResultRow[status]
                )
            }
        } catch (e: Exception) {
            null
        }
    }

    fun setTaskName(newName:String){
        transaction {
            update {
                it[name] = newName
            }
        }
    }

    fun setTaskStatus(taskId:String, newStatus: String) {
        transaction {
            update({TasksTable.id eq taskId}) {
                it[status] = newStatus
            }
        }
    }

    fun setTaskDescription(newDescription: String) {
        transaction {
            update {
                it[description] = description
            }
        }
    }
}