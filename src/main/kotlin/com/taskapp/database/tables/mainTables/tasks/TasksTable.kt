package com.taskapp.database.tables.mainTables.tasks

import com.taskapp.database.stringTypes.TaskStatus
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.logging.Filter

object TasksTable : Table() {
    private val id = varchar("id", 50)
    private val name = varchar("name", 25)
    private val description = varchar("description", 1000)
    private val status = varchar("status", 25)
    private val deadLine = varchar("deadLine", 30)
    private val creationDate = varchar("creationDate", 30)

    fun insertTable(taskDAO: TaskDAO) {
        transaction {
            insert {
                it[id] = taskDAO.id
                it[name] = taskDAO.name
                it[description] = taskDAO.description
                it[status] = taskDAO.status
                it[deadLine] = taskDAO.deadLine
                it[creationDate] = taskDAO.creationDate
            }
        }
    }

    fun getTaskById(id: String): TaskDAO? {
        return try {
            transaction {
                val userResultRow = TasksTable.select { TasksTable.id.eq(id) }.single()
                TaskDAO(
                    id = userResultRow[TasksTable.id],
                    name = userResultRow[name],
                    description = userResultRow[description],
                    status = userResultRow[status],
                    deadLine = userResultRow[deadLine],
                    creationDate = userResultRow[creationDate]
                )
            }
        } catch (e: Exception) {
            null
        }
    }

    fun setTaskName(taskId: String, newName: String) {
        transaction {
            update({ TasksTable.id eq taskId }) {
                it[name] = newName
            }
        }
    }

    fun setTaskStatus(taskId: String, newStatus: String) {
        transaction {
            update({ TasksTable.id eq taskId }) {
                it[status] = newStatus
            }
        }
    }

    fun setTaskDescription(taskId: String, newDescription: String) {
        transaction {
            update({ TasksTable.id eq taskId }) {
                it[description] = newDescription
            }
        }
    }

    fun deleteTask(taskId: String) {
        transaction {
            deleteWhere { TasksTable.id eq taskId }
        }
    }
}