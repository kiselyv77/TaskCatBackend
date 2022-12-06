package com.taskapp.database.tables.mainTables.notes

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object NotesTable : Table() {
    private val id = NotesTable.varchar("id", 50)
    private val info = NotesTable.varchar("info", 50)
    private val loginUser = NotesTable.varchar("loginUser", 50)
    private val userName = NotesTable.varchar("userName",50)
    private val taskId = NotesTable.varchar("taskId", 50)
    private val attachmentFile = NotesTable.varchar("attachmentFile", 50)
    private val dateTime = varchar("dateTime", 30)

    fun addNote(noteDAO: NoteDAO) {
        transaction {
            insert {
                it[NotesTable.id] = noteDAO.id
                it[NotesTable.info] = noteDAO.info
                it[NotesTable.loginUser] = noteDAO.loginUser
                it[NotesTable.userName] = noteDAO.userName
                it[NotesTable.taskId] = noteDAO.taskId
                it[NotesTable.attachmentFile] = noteDAO.attachmentFile
                it[NotesTable.dateTime] = noteDAO.dateTime
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
                        dateTime = it[NotesTable.dateTime]
                    )
                }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}