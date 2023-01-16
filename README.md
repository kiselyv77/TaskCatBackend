# TaskCatBackend
Беккенд для приложения на андроид.(Приложение для менеджмента задач)
- Андроид приложение: https://github.com/kiselyv77/TasksApp
- Сайт приложеняй: http://task-cat.tilda.ws (там можно скачать приложение и протестировать)

Бекенд написан на фреймвонре Ktor. Для сериализации используется kotlin serialization. Для взаимодействия с базой данных postgresql используется библиотека exposed.
Реализованна аутентификация с помощью токенов доступа. Приложение полностью готово к использованию.


<p><h3>Основные технологии и библиотеки используемые проэкте:</h3></p>

- Ktor.
- Web sockets.
- Kotlin coroutines.
- Exposed
- Kotlin serialization
- Postgresql

<p><h3>Запросы:</h3></p>

- /register
- /login
- /getUserByToken/{token}
- /addWorkSpace
- /getWorkSpaces/{token}
- /getWorkSpaceById/{token}/{id}
- getTasksFromWorkSpace/{token}/{workSpaceId}
- /addTaskToWorkSpace
- /addUserToWorkSpace
- /getUsersFromWorkSpace/{token}/{workSpaceId}
- /setTaskStatus/{token}/{taskId}/{newStatus}
- /setUserStatus/{token}/{newStatus}
- /getMessagesFromWorkSpace/{token}/{workSpaceId}/{offset}
- /getTaskById/{token}/{id}
- /setUserStatusToWorkSpace/{token}/{userLogin}/{workSpaceId}/{newStatus}
- /uploadNewAvatar/{token}
- /uploadFileVoiceMessage/{token}
- /getNotesFromTask/{token}/{taskId}/{offset}
- /setTaskDeadLine/{token}/{taskId}/{newDeadLine}
- /getUsersFromTask/{token}/{taskId}
- /addUserToTask/{token}/{userLogin}/{taskId}
- /deleteWorkSpace/{token}/{workSpaceId}
- /deleteUserFromWorkSpace/{token}/{workSpaceId}/{userLogin}
- /deleteUserFromTask/{token}/{taskId}/{userLogin}
- /deleteTask/{token}/{taskId}
- /getTasksFromWorkSpaceForUser/{token}/{workSpaceId}
- /uploadNoteAttachmentFile/{token}
- /getNoteAttachmentFile/{fileName}


 
