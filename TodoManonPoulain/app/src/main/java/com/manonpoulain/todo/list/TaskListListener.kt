package com.manonpoulain.todo.list

interface TaskListListener {

    fun onClickDelete(task: Task)
    fun onClickEdit(task: Task)

    fun onLongClickListener(task: Task) : Boolean
}