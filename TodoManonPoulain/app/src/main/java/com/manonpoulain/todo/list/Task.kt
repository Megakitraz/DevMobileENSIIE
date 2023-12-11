package com.manonpoulain.todo.list

data class Task(val id : String, val title : String, val description : String = "Description de base") : java.io.Serializable {

}