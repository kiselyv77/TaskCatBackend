package com.taskapp.database.tables.mainTables.messages

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object MessagesTable : Table() {
    private val id = varchar("id", 50)
    private val text = varchar("text", 500)
    private val dateTime = varchar("dateTime", 30)
    private val type = varchar("type", 30)
    private val sendingUser = varchar("sendingUser", 25)
    private val workSpaceId = varchar("workSpaceId", 50)
    private val fileName = varchar("fileName", 50)

    fun insertMessage(messageDAO: MessageDAO) {
        transaction {
            insert {
                it[id] = messageDAO.id
                it[text] = messageDAO.text
                it[dateTime] = messageDAO.dateTime
                it[type] = messageDAO.type
                it[sendingUser] = messageDAO.sendingUser
                it[workSpaceId] = messageDAO.workSpaceId
                it[fileName] = messageDAO.fileName
            }
        }
    }

    fun getMessagesFromWorkSpace(workSpaceId: String, offset: Int): List<MessageDAO> {
        return try {
            transaction {
                MessagesTable.select {
                    MessagesTable.workSpaceId.eq(workSpaceId)
                }.orderBy(MessagesTable.dateTime to SortOrder.DESC)
                    .limit(10, offset.toLong())
                    .toList().map {
                        MessageDAO(
                            id = it[MessagesTable.id],
                            text = it[text],
                            dateTime = it[dateTime],
                            type = it[type],
                            sendingUser = it[sendingUser],
                            workSpaceId = it[MessagesTable.workSpaceId],
                            fileName = it[MessagesTable.fileName]
                        )
                    }
            }
        } catch (e: Exception) {
            println(e)
            emptyList()
        }
    }

    fun setMessageText(messageId: String, newMessage: String) {
        transaction {
            update({ MessagesTable.id eq messageId }) {
                it[MessagesTable.text] = newMessage
            }
        }
    }

    fun deleteMessage(messageId: String) {
        transaction {
            deleteWhere {
                MessagesTable.id eq messageId
            }
        }
    }
}