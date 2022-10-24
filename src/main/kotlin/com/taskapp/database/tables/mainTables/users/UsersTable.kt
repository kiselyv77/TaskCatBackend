package com.taskapp.database.tables.mainTables.users

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object UsersTable: Table() {
    private val name = UsersTable.varchar("name", 25)
    private val login = UsersTable.varchar("login", 25)
    private val status = UsersTable.varchar("status", length = 25)
    private val password = UsersTable.varchar("password", 25)

    fun insertUser(userDAO: UserDAO){
        transaction {
            insert {
                it[name] = userDAO.name
                it[login] = userDAO.login
                it[status] = userDAO.status
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
                    status = userResultRow[status],
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
                        login = it[login],
                        status = it[status],
                        password = it[password],
                    )
                }.filter { it.name.contains(searchQuery) }
            }
        }catch (e: Exception) {
            emptyList()
        }
    }

    fun setUserStatus(login: String , newStatus: String){
        transaction {
            update({ UsersTable.login eq login}) {
                it[status] = newStatus
            }
        }
    }


}