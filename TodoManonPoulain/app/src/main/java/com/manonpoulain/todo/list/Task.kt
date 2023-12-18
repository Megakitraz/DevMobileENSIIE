package com.manonpoulain.todo.list

import android.os.Parcelable

data class Task(val id : String, val title : String, val description : String = "Description de base") : java.io.Serializable {

}