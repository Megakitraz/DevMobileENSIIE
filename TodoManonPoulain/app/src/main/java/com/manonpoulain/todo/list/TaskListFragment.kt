package com.manonpoulain.todo.list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.manonpoulain.todo.R
import com.manonpoulain.todo.data.Api
import com.manonpoulain.todo.data.TasksListViewModel
import com.manonpoulain.todo.detail.DetailActivity
import com.manonpoulain.todo.user.UserActivity
import kotlinx.coroutines.launch

class TaskListFragment : Fragment() {

    //private var taskList = listOf("Task 1", "Task 2", "Task 3")

    private val viewModel: TasksListViewModel by viewModels()

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
        val imageAvatarButton = view.findViewById<ImageView>(R.id.imageAvatar)

        val intentDetail = Intent(context, DetailActivity::class.java)
        val intentUser = Intent(context, UserActivity::class.java)
        floatingActionButton.setOnClickListener{
            //val newTask = Task(id = UUID.randomUUID().toString(), title = "Task ${taskList.size + 1}")
            //taskList = taskList + newTask
            //adapter.submitList(taskList)
            //startActivity(intent)
            createTask.launch(intentDetail)
        }

        imageAvatarButton.setOnClickListener{

            startActivity(intentUser)
        }

        //var sizeTaskList = savedInstanceState?.getSerializable("nbTask")//.toString().toInt()
        val sizeTaskList = savedInstanceState?.getSerializable("tasklist") as? Array<Task>

        taskList = sizeTaskList?.toList() ?: emptyList()

        recyclerView.adapter = adapter
        adapter.submitList(taskList)

        lifecycleScope.launch { // on lance une coroutine car `collect` est `suspend`
            viewModel.tasksStateFlow.collect { newList ->
                // cette lambda est exécutée à chaque fois que la liste est mise à jour dans le VM
                // -> ici, on met à jour la liste dans l'adapter
                adapter.submitList(newList)
            }
        }

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

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            // Ici on ne va pas gérer les cas d'erreur donc on force le crash avec "!!"
            val user = Api.userWebService.fetchUser().body()!!
            val userTextView = view?.findViewById<TextView>(R.id.userTextView)
            val userImageAvatar = view?.findViewById<ImageView>(R.id.imageAvatar)
            if (userTextView != null) {
                userTextView.text = user.name
            }

            if(userImageAvatar != null){
                userImageAvatar.load("https://goo.gl/gEgYUd")
            }

        }
        viewModel.refresh()

    }




}