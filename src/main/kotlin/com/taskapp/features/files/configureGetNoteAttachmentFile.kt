package com.taskapp.features.files

import com.taskapp.utils.getFileSeparator
import com.taskapp.utils.getRootPackage
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureGetNoteAttachmentFile() {
    routing {
        get("/getNoteAttachmentFile/{fileName}") {
            val fileName = call.parameters["fileName"]
            val sep = getFileSeparator()
            val file = File("${getRootPackage()}${sep}notesAttachmentFiles${sep}$fileName")
            call.respondFile(file)
        }
    }
}