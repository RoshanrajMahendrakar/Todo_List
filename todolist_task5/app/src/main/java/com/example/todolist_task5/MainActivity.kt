package com.example.todolist_task5

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {

    private lateinit var taskList: MutableList<Task>
    private lateinit var listView: ListView
    private lateinit var adapter: Todo_CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listViewTasks)
        taskList = getTasksFromPreferences()

        // Initialize the adapter with the task list
        adapter = Todo_CustomAdapter(this, taskList)
        listView.adapter = adapter

        val addTaskButton = findViewById<Button>(R.id.button_add_task)
        addTaskButton.setOnClickListener {
            // Open the dialog fragment to add a new task
            val dialog = AddTaskDialogFragment()
            dialog.setListener(object : AddTaskDialogFragment.AddTaskDialogListener {
                override fun onDialogPositiveClick(task: String) {
                    val taskDetails = task.split("\n")
                    val taskName = taskDetails[0].substringAfter("Title: ").trim()
                    val taskDescription = taskDetails[1].substringAfter("Description: ").trim()
                    val newTask = Task(taskName, taskDescription, "")
                    taskList.add(newTask)
                    saveTasksToPreferences(taskList)
                    adapter.notifyDataSetChanged()
                }
            })
            dialog.show(supportFragmentManager, "AddTaskDialogFragment")
        }
    }

    // Fetch tasks from SharedPreferences
    private fun getTasksFromPreferences(): MutableList<Task> {
        val sharedPreferences: SharedPreferences = getSharedPreferences("tasks_pref", MODE_PRIVATE)
        val tasks = sharedPreferences.getStringSet("tasks", mutableSetOf())?.map {
            val parts = it.split("|")
            if (parts.size == 3) {
                Task(parts[0], parts[1], parts[2])
            } else {
                Task("", "", "")
            }
        }?.toMutableList() ?: mutableListOf()
        return tasks
    }

    // Save tasks to SharedPreferences
    private fun saveTasksToPreferences(tasks: MutableList<Task>) {
        val sharedPreferences: SharedPreferences = getSharedPreferences("tasks_pref", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val updatedTaskSet = tasks.map { "${it.name}|${it.description}|${it.dateTime}" }.toSet()
        editor.putStringSet("tasks", updatedTaskSet)
        editor.apply()
    }
}
