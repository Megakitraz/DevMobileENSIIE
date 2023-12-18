package com.manonpoulain.todo.list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.manonpoulain.todo.R
import com.manonpoulain.todo.detail.DetailActivity
import java.lang.Integer.parseInt
import java.util.UUID

class TaskListFragment : Fragment() {

    //private var taskList = listOf("Task 1", "Task 2", "Task 3")

    val createTask = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        // dans cette callback on récupèrera la task et on l'ajoutera à la liste
        val task = result.data?.getSerializableExtra("task") as Task?
        if (task != null)
            taskList = taskList + task
        adapter.submitList(taskList)
    }

    val editTask = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = result.data?.getSerializableExtra("task") as Task?
        if(task != null) {
            taskList = taskList.map { if (it.id == task.id) task else it }

        }
        adapter.submitList(taskList)
    }

    private var taskList = listOf(
        Task(id = "id_1", title = "Task 1", description = "description 1"),
        Task(id = "id_2", title = "Task 2"),
        Task(id = "id_3", title = "Task 3")
    )
    val adapterListener : TaskListListener = object : TaskListListener {
        override fun onClickDelete(task: Task) {
            taskList = taskList - task
            adapter.submitList(taskList)
        }

        override fun onClickEdit(task: Task) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("task", task)
            editTask.launch(intent)
        }

        override fun onLongClickListener(task: Task) : Boolean {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Titre: "+ task.title+ "\nDescription: "+ task.description)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
            return true
        }
    }
    private val adapter = TaskListAdapter(adapterListener)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_task_list, container, false)
        //adapter.currentList = taskList

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.manonpoulain)
        val floatingActionButton = view.findViewById<FloatingActionButton>(R.id.floatingActionButton2)
        val intent = Intent(context, DetailActivity::class.java)
        floatingActionButton.setOnClickListener{
            //val newTask = Task(id = UUID.randomUUID().toString(), title = "Task ${taskList.size + 1}")
            //taskList = taskList + newTask
            //adapter.submitList(taskList)
            //startActivity(intent)
            createTask.launch(intent)
        }

        //var sizeTaskList = savedInstanceState?.getSerializable("nbTask")//.toString().toInt()
    var sizeTaskList = savedInstanceState?.getSerializable("tasklist") as? Array<Task>

        taskList = sizeTaskList?.toList() ?: emptyList()
        /*
        if (sizeTaskList != null) {
            for (i in 0..sizeTaskList-1){

                var taskid = savedInstanceState?.getString("task $i id")
                var tasktitle = savedInstanceState?.getString("task $i title")
                var taskdescription = savedInstanceState?.getString("task $i description")
                if(taskid == null) continue
                if(tasktitle == null) continue
                if(taskdescription == null) continue
                taskList.plus(Task(taskid,tasktitle,taskdescription))
            }
        }*/

        /*
        adapter.onClickDelete =  { task ->
            taskList = taskList - task
            adapter.submitList(taskList)
        }

        adapter.onClickEdit =  { task ->
            intent.putExtra("task",task)
            editTask.launch(intent)
        }
        */

        //super.onViewCreated(view, savedInstanceState)
        //recyclerView.adapter = adapter

        recyclerView.adapter = adapter
        adapter.submitList(taskList)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable("tasklist",taskList.toTypedArray())
        super.onSaveInstanceState(outState)
        //outState.putSerializable("nbTask",taskList.size)
        //var i = 0
        /*
        for(task in taskList){
            outState.putSerializable("task $i id",task)
            outState.putSerializable("task $i title",task.title)
            outState.putSerializable("task $i description",task.description)
            i++
        }*/


    }


}