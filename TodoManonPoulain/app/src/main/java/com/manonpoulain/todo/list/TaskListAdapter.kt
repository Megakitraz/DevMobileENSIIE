package com.manonpoulain.todo.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.manonpoulain.todo.R

object MyItemsDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task) : Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task) : Boolean {
        return oldItem.title == newItem.title && oldItem.description == newItem.description
    // comparaison: est-ce le même "contenu" ? => mêmes valeurs? (avec data class: simple égalité)
    }
}

class TaskListAdapter : ListAdapter<Task, TaskListAdapter.TaskViewHolder>(MyItemsDiffCallback) {


    // on utilise `inner` ici afin d'avoir accès aux propriétés de l'adapter directement
    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {




        fun bind(task: Task) {
            itemView.findViewById<TextView>(R.id.task_title).setText(task.title);
            itemView.findViewById<TextView>(R.id.textDescriptor).setText(task.description);
            //itemView.findViewById<ImageButton>(R.id.imageButton)
            

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(itemView);
    }


    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

        holder.bind(currentList[position])

    }

    fun refreshAdapter(){

    }


    // Déclaration de la variable lambda dans l'adapter:
    var onClickDelete: (Task) -> Unit = {}





}


// Usage is simpler:
//val taskListAdapter = TaskListAdapter()