package com.manonpoulain.todo.data

import com.manonpoulain.todo.list.Task
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TasksWebService {
    @GET("/rest/v2/tasks/")
    suspend fun fetchTasks(): Response<List<Task>>

    @POST("/rest/v2/tasks/")
    suspend fun create(@Body task: Task): Response<Task>

    @POST("/rest/v2/tasks/{id}")
    suspend fun update(@Body task: Task, @Path("id") id: String = task.id): Response<Task>

// Complétez avec les méthodes précédentes, la doc de l'API, et celle de Retrofit:
    @POST
    suspend fun delete(@Path("id") id: String): Response<Unit>
}