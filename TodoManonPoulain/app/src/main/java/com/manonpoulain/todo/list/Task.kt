package com.manonpoulain.todo.list

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Task(
    @SerialName("id")
    val id : String,
    @SerialName("title")
    val title : String,
    @SerialName("description")
    val description : String = "Description de base")
    : java.io.Serializable {

}