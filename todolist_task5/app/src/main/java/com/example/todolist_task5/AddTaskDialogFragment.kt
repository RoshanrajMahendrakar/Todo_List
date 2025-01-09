package com.example.todolist_task5

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*

class AddTaskDialogFragment : BottomSheetDialogFragment() {

    interface AddTaskDialogListener {
        fun onDialogPositiveClick(task: String)
    }

    private var listener: AddTaskDialogListener? = null

    fun setListener(listener: AddTaskDialogListener) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_task_dialog, container, false)

        val taskNameEditText = view.findViewById<EditText>(R.id.editTextTaskName)
        val taskDescriptionEditText = view.findViewById<EditText>(R.id.editTextTaskDescription)
        val scheduledDateEditText = view.findViewById<EditText>(R.id.editTextScheduledDate)
        val submitButton = view.findViewById<Button>(R.id.submitButton)

        // Date Picker
        scheduledDateEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
                calendar.set(year, month, day)
                val date = "${day}/${month + 1}/${year}"
                scheduledDateEditText.setText(date)
            }
            DatePickerDialog(requireContext(), dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        submitButton.setOnClickListener {
            val taskName = taskNameEditText.text.toString().trim()
            val taskDescription = taskDescriptionEditText.text.toString().trim()

            if (taskName.isNotEmpty() && taskDescription.isNotEmpty()) {
                val taskDetails = "Title: $taskName\nDescription: $taskDescription"
                listener?.onDialogPositiveClick(taskDetails)
                dismiss()
            } else {
                taskNameEditText.error = "Task name cannot be empty"
                taskDescriptionEditText.error = "Description cannot be empty"
            }
        }

        return view
    }
}
