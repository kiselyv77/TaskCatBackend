package com.taskapp.database.tables.mainTables.notes

import com.taskapp.database.tables.mainTables.tasks.TasksTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object NotesTable : Table() {
    private val id = NotesTable.varchar("id", 50)
    private val info = NotesTable.varchar("info", 500)
    private val loginUser = NotesTable.varchar("loginUser", 50)
    private val userName = NotesTable.varchar("userName",50)
    private val taskId = NotesTable.varchar("taskId", 50)
    private val attachmentFile = NotesTable.varchar("attachmentFile", 1000)
    private val timeStamp = varchar("timeStamp", 30)

    fun addNote(noteDAO: NoteDAO) {
        transaction {
            insert {
                it[NotesTable.id] = noteDAO.id
                it[NotesTable.info] = noteDAO.info
                it[NotesTable.loginUser] = noteDAO.loginUser
                it[NotesTable.userName] = noteDAO.userName
                it[NotesTable.taskId] = noteDAO.taskId
                it[NotesTable.attachmentFile] = noteDAO.attachmentFile
                it[NotesTable.timeStamp] = noteDAO.timeStamp
            }
        }
    }

    fun getNotes(taskId: String, offset: Int): List<NoteDAO> {
        return try {
            transaction {
                NotesTable.select {
                    NotesTable.taskId.eq(taskId)
                }.limit(10, offset.toLong()).reversed().toList().map {
                    NoteDAO(
                        id = it[NotesTable.id],
                        info = it[NotesTable.info],
                        loginUser = it[NotesTable.loginUser],
                        userName = it[NotesTable.userName],
                        taskId = it[NotesTable.taskId],
                        attachmentFile = it[NotesTable.attachmentFile],
                        timeStamp = it[NotesTable.timeStamp]
                    )
                }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun deleteAllNotesFromTask(taskId: String){
        transaction {
            deleteWhere { NotesTable.taskId eq taskId }
        }
    }
}