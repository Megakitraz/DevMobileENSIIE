@file:OptIn(ExperimentalMaterial3Api::class)

package com.manonpoulain.todo.detail

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
import java.util.UUID

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoManonPoulainTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Detail(onValidate = {
                        intent.putExtra("task", it)
                        setResult(RESULT_OK, intent)
                        finish()
                    })
                }
            }
        }




    }
}

@Composable
fun Detail(onValidate: (Task) -> Unit, modifier: Modifier = Modifier) {
    var task by remember { mutableStateOf(Task(UUID.randomUUID().toString(),"","")) }
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
                val newTask = Task(id = UUID.randomUUID().toString(), title = task.title, description = task.description);
                onValidate(newTask)

        }) {

        }

    }

}

@Preview(showBackground = true)
@Composable
fun DetailPreview() {
    TodoManonPoulainTheme {
        Detail({})
    }
}