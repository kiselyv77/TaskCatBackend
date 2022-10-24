package com.taskapp.database.tables.mainTables.subTasks

import com.taskapp.database.tables.mainTables.tasks.TaskDAO
import com.taskapp.database.tables.mainTables.tasks.TasksTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object SubTasksTable: Table() {
    private val id = varchar("id", 50)
    private val name = varchar("name", 25)
    private val description = varchar("description", 1000)
    private val status = varchar("status", 25)

    fun addSubTask(subTaskDAO: SubTaskDAO){
        transaction {
            insert {
                it[id] = subTaskDAO.id
                it[name] = subTaskDAO.name
                it[description] = subTaskDAO.description
                it[status] = subTaskDAO.status
            }
        }
    }

    fun getSubTaskById(id: String): SubTaskDAO? {
        return try {
            transaction {
                val userResultRow = TasksTable.select { SubTasksTable.id.eq(id) }.single()
                SubTaskDAO(
                    id = userResultRow[SubTasksTable.id],
                    name = userResultRow[name],
                    description = userResultRow[description],
                    status = userResultRow[status]
                )
            }
        } catch (e: Exception) {
            null
        }
    }
}