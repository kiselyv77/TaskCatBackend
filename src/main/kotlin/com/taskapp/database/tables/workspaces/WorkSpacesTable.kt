package com.taskapp.database.tables.workspaces

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object WorkSpacesTable: Table() {

    private val id = varchar("id", 50)
    private val name = varchar("name", 25)
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

    fun getWorkSpaces(loginUser: String): List<WorkSpacesDAO> {
        return try {
            transaction {
                WorkSpacesTable.select{
                    WorkSpacesTable.creator.eq(loginUser)
                }.toList().map {
                    WorkSpacesDAO(
                        id = it[WorkSpacesTable.id],
                        name = it[name],
                        description = it[description],
                        creator = it[creator],
                    )
                }
            }
        }catch (e: Exception) {
            emptyList()
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
}