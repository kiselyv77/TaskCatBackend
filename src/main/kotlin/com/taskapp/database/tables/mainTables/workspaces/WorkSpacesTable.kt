package com.taskapp.database.tables.mainTables.workspaces

import com.taskapp.database.tables.mainTables.tasks.TasksTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object WorkSpacesTable: Table() {

    private val id = varchar("id", 50)
    private val name = varchar("name", 100)
    private val description = varchar("description", 1000)
    private val creator = varchar("creator", 25)

    fun insertWorkSpace(workSpacesDAO: WorkSpacesDAO){
        transaction {
            insert {
                it[id] = workSpacesDAO.id
                it[name] = workSpacesDAO.name
                it[description] = workSpacesDAO.description
                it[creator] = workSpacesDAO.creator
            }
        }
    }

    fun getWorkSpaceById(workSpaceId:String): WorkSpacesDAO?{
        return try {
            transaction {
                val model =  WorkSpacesTable.select {
                    WorkSpacesTable.id.eq(workSpaceId)
                }.single()
                WorkSpacesDAO(
                    id = model[WorkSpacesTable.id],
                    name = model[name],
                    description = model[description],
                    creator = model[creator]

                )
            }
        }
        catch (e:Exception){
            null
        }
    }

    fun setWorkSpaceName(workSpaceId: String, newName:String){
        transaction {
            update({ WorkSpacesTable.id eq workSpaceId }) {
                it[WorkSpacesTable.name] = newName
            }
        }
    }
    fun setWorkSpaceDescription(workSpaceId: String, newDescription: String){
        transaction {
            update({ WorkSpacesTable.id eq workSpaceId }) {
                it[WorkSpacesTable.description] = newDescription
            }
        }
    }

    fun deleteWorkSpace(workSpaceId: String){
        transaction {
            deleteWhere { WorkSpacesTable.id eq workSpaceId }
        }
    }

}