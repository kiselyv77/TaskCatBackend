package com.taskapp.features.addUserToWorkSpace

import com.taskapp.database.tables.tokens.TokensTable
import com.taskapp.database.stringTypes.UserTypes.ADMIN_TYPE
import com.taskapp.database.stringTypes.UserTypes.MEMBER_TYPE
import com.taskapp.database.tables.usersToWorkSpaces.UserToWorkSpaceDAO
import com.taskapp.database.tables.usersToWorkSpaces.UserToWorkSpacesTable
import com.taskapp.database.tables.workspaces.WorkSpacesTable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class AddUserToWorkSpaceController(val call: ApplicationCall) {

    suspend fun addUserToWorkSpace(){
        // Обект запроса (то что нам передал пользователь)
        val receive = call.receive<AddUserToWorkSpaceReceiveDTO>()
        // Полусаем все токены
        val tokens = TokensTable.getTokens()
        // С помощью токена находим логин пользователя который отсылает запрос
        val loginUser = tokens.filter { it.token == receive.token } // Сдесь содержится логин пользователя отославшего запрос
        // Если поусто значит пользлователь не авторизован в системе
        if(loginUser.isNotEmpty()){
            val workSpace = WorkSpacesTable.getWorkSpaceById(receive.workSpaceId)
            println(workSpace)
            val user = UserToWorkSpacesTable.getUserFromWorkSpace(receive.workSpaceId).first { it.userLogin == loginUser.first().login }
            // Проверяем является ли пользователь который приглашает создателем или администратором этого рабочего пространства
            if(workSpace?.creator == loginUser.first().login || user.userStatusToWorkSpace == ADMIN_TYPE){
                // Добовляем только если такого пользователя еще нет в этом рабочем пространстве
                if(UserToWorkSpacesTable.getUserFromWorkSpace(receive.workSpaceId).none { it.userLogin == receive.invitedUserLogin }
                ){
                    // Вставляем пользователя в рабочее пространство
                    UserToWorkSpacesTable.insertUserToWorkSpace(
                        UserToWorkSpaceDAO(
                            workSpacesId = receive.workSpaceId,
                            userLogin = receive.invitedUserLogin,
                            userStatusToWorkSpace = MEMBER_TYPE // По умолчанию даем ему статус обычного пользователя
                        )
                    )
                    call.respond("Вы пригласили пользователя ${receive.invitedUserLogin} в рабочее пространство ${receive.workSpaceId}")
                }
                else{
                    call.respond(HttpStatusCode.BadRequest, "Этот пользователь уже есть в этом рабочем пространстве")
                }
            }
            else{
                call.respond(HttpStatusCode.BadRequest, "Вы должны быть администратором или создателем этого рабочего пространства")
            }
        }
        else{
            call.respond(HttpStatusCode.BadRequest, "Вы не авторизованны в системе!")
        }
    }
}