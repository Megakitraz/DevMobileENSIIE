@file:OptIn(ExperimentalMaterial3Api::class)

package com.manonpoulain.todo.detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.manonpoulain.todo.detail.ui.theme.TodoManonPoulainTheme
import com.manonpoulain.todo.list.Task
import com.manonpoulain.todo.list.TaskListFragment
import java.util.UUID

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var task = intent.getSerializableExtra("task") as Task?
        when {
            intent?.action == Intent.ACTION_SEND -> {
                if ("text/plain" == intent.type) {
                    intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
                        task = Task(id = UUID.randomUUID().toString(), title = "Task importée", description = it )
                    }
                }
            }
        }
        setContent {
            TodoManonPoulainTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Detail(onValidate = { newTask ->
                        intent.putExtra("task", newTask)
                        setResult(RESULT_OK, intent)
                        finish()
                    }, initialTask = task)

                }
            }
        }


    }

}

@Composable
fun Detail(onValidate: (Task) -> Unit, modifier: Modifier = Modifier, initialTask: Task?) {
    var task by remember { mutableStateOf(initialTask ?: Task(UUID.randomUUID().toString(),"","")) }
    Column(modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {

        Text(
            text = "Task Detail",
            style=MaterialTheme.typography.headlineLarge,
            modifier = modifier
        )
        OutlinedTextField(
            value=task.title,
            onValueChange = {task = task.copy(title = it)},
            label={Text("Title")}
            //text = "Title"
        )

        OutlinedTextField(
            value=task.description,
            onValueChange = {task = task.copy(description = it)},
            label={Text("Description")}
        )
        Button(
            onClick = {
                val newTask = Task(id = task.id, title = task.title, description = task.description);
                onValidate(newTask)

        }) {

        }

    }

}


/*
@Preview(showBackground = true)
@Composable
fun DetailPreview() {
    TodoManonPoulainTheme {
        Detail({})
    }
}
*/
