package com.taskapp.database.tables.users

import com.taskapp.database.tables.tasksToWorkSpaces.TaskToWorkSpaceDAO
import com.taskapp.database.tables.tasksToWorkSpaces.TaskToWorkSpacesTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object UsersTable: Table() {
    private val name = UsersTable.varchar("name", 25)
    private val login = UsersTable.varchar("login", 25)
    private val password = UsersTable.varchar("password", 25)

    fun insertUser(userDAO: UserDAO){
        transaction {
            insert {
                it[name] = userDAO.name
                it[login] = userDAO.login
                it[password] = userDAO.password
            }
        }
    }

    fun getUser(login:String): UserDAO?{
        return try {
            if(login.isEmpty()){return null}
            transaction {
                val userResultRow = UsersTable.select { UsersTable.login.eq(login) }.single()
                UserDAO(
                    name = userResultRow[name],
                    login = userResultRow[UsersTable.login],
                    password = userResultRow[password],
                )
            }
        }
        catch (e:Exception){
            null
        }
    }

    fun getUsers(searchQuery:String):List<UserDAO>{
        return try {
            transaction {
                UsersTable.selectAll().toList().map {
                    UserDAO(
                        name = it[name],
                        login = it[UsersTable.login],
                        password = it[password],
                    )
                }.filter { it.name.contains(searchQuery) }
            }
        }catch (e: Exception) {
            emptyList()
        }
    }
}