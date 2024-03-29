package com.taskapp.features.files

import com.taskapp.database.tables.mainTables.tokens.TokensTable
import com.taskapp.utils.SucsefullResponse
import com.taskapp.utils.getFileSeparator
import com.taskapp.utils.getRootPackage
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureUploadNewAvatar() {
    routing {
        post("/uploadNewAvatar/{token}") {
            val token = call.parameters["token"]
            val tokens = TokensTable.getTokens()
            val loginUser = tokens.last { it.token == token }.login // по токену определяем логин
            val sep = getFileSeparator()
            val file = File("${getRootPackage()}${sep}avatars${sep}${loginUser}_avatar.jpg")

            val multipart = call.receiveMultipart()

            multipart.forEachPart { part ->
                // if part is a file (could be form item)
                if(part is PartData.FileItem) {
                    // use InputStream from part to save file
                    part.streamProvider().use { its ->
                        // copy the stream to the file with buffering
                        file.outputStream().buffered().use {
                            // note that this is blocking
                            its.copyTo(it)
                        }
                    }
                }
                // make sure to dispose of the part after use to prevent leaks
                part.dispose()
                call.respond(SucsefullResponse("SucsefullResponse"))
            }
        }
    }
}