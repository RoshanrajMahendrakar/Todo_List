package com.example.todolist_task5

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView


class Todo_CustomAdapter(
    context: Context,
    private val tasks: MutableList<Task>
) : ArrayAdapter<Task>(context, 0, tasks) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.task_item, parent, false)

        val taskNameTextView: TextView = view.findViewById(R.id.textViewTaskName)
        val taskDescriptionTextView: TextView = view.findViewById(R.id.textViewTaskDescription)
        val taskDateTimeTextView: TextView = view.findViewById(R.id.textViewTaskDateTime)
        val deleteButton: Button = view.findViewById(R.id.buttonDelete)

        val task = tasks[position]
        taskNameTextView.text = task.name
        taskDescriptionTextView.text = task.description
        taskDateTimeTextView.text = task.dateTime

        deleteButton.setOnClickListener {
            tasks.removeAt(position)
            saveTasksToPreferences(context, tasks)
            Log.d("Todo_CustomAdapter", "Task deleted. Updated tasks: $tasks")
            notifyDataSetChanged()
        }

        return view
    }

    private fun saveTasksToPreferences(context: Context, tasks: List<Task>) {
        val sharedPreferences = context.getSharedPreferences("tasks_pref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val updatedTaskSet = tasks.map { "${it.name}|${it.description}|${it.dateTime}" }.toSet()
        editor.putStringSet("tasks", updatedTaskSet)
        editor.apply()
    }
}
data class Task(
    val name: String,
    val description: String,
    val dateTime: String
)
