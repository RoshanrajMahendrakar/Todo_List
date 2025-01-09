package com.example.todolist_task5

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class TodoDisplayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_display)

        val sharedPreferences: SharedPreferences = getSharedPreferences("tasks_pref", MODE_PRIVATE)
        val tasks = sharedPreferences.getStringSet("tasks", mutableSetOf())?.toList() ?: listOf()

        val listView = findViewById<ListView>(R.id.listViewTasks)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tasks)
        listView.adapter = adapter
    }
}
