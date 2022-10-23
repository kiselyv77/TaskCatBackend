package com.taskapp.database.tables.messages

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object MessagesTable :Table(){
    private val id = varchar("id", 50)
    private val text = varchar("text", 500)
    private val dateTime = varchar("dateTime", 30)
    private val sendingUser = varchar("sendingUser", 25)
    private val workSpaceId = varchar("workSpaceId", 50)


    fun insertMessage(messageDAO: MessageDAO) {
        transaction {
            insert {
                it[MessagesTable.id] = messageDAO.id
                it[MessagesTable.text] = messageDAO.text
                it[MessagesTable.dateTime] = messageDAO.dateTime
                it[MessagesTable.sendingUser] = messageDAO.sendingUser
                it[MessagesTable.workSpaceId] = messageDAO.workSpaceId
            }
        }
    }

    fun getMessagesFromWorkSpace(workSpaceId: String):List<MessageDAO> {
        return try {
            transaction {
                MessagesTable.select{
                    MessagesTable.workSpaceId.eq(workSpaceId)
                }.toList().map {
                    MessageDAO(
                        id = it[MessagesTable.id],
                        text = it[MessagesTable.text],
                        dateTime = it[MessagesTable.dateTime],
                        sendingUser =  it[MessagesTable.sendingUser],
                        workSpaceId = it[MessagesTable.workSpaceId],
                    )
                }
            }
        }catch (e: Exception) {
            emptyList()
        }
    }
}