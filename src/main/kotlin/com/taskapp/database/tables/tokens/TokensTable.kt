package com.taskapp.database.tables.tokens

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object TokensTable: Table() {

    private val login = TokensTable.varchar("login", 25)
    private val token = TokensTable.varchar("token", 50)

    fun insertToken(userDAO: TokenDAO){
        transaction {
            insert {
                it[login] = userDAO.login
                it[token] = userDAO.token
            }
        }
    }

    fun getTokens(): List<TokenDAO>{
        return try {
            transaction {
                TokensTable.selectAll().toList().map {
                    TokenDAO(
                        token = it[token],
                        login = it[login]
                    )
                }
            }
        }catch (e: Exception) {
            emptyList()
        }
    }

}