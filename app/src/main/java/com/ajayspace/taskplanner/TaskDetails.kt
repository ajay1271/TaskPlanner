package com.ajayspace.taskplanner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider

import com.ajayspace.taskplanner.database.DashboardViewModelFactory
import com.ajayspace.taskplanner.database.TasksDatabase
import com.ajayspace.taskplanner.database.TodoItem
import com.ajayspace.taskplanner.databinding.FragmentTaskDetailsBinding


class TaskDetails : Fragment() {

    private lateinit var fragmentTaskDetailsBinding: FragmentTaskDetailsBinding
    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var task: TodoItem;
    private lateinit var spinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentTaskDetailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_task_details, container, false)

        //get props
        task = TaskDetailsArgs.fromBundle(requireArguments()).taskItem

        fragmentTaskDetailsBinding.taskTitle.text = task.title
        fragmentTaskDetailsBinding.taskDescription.text = task.desc


        //spinner
        spinner = fragmentTaskDetailsBinding.taskStatus

        activity?.let {
            ArrayAdapter.createFromResource(
                it.applicationContext,
                R.array.task_status_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }
        }


        //shared viewModel
        val application = requireNotNull(this.activity).application
        val datasource = TasksDatabase.getInstance(application).taskDao
        val viewModelFactory = DashboardViewModelFactory(datasource, application)
        dashboardViewModel =
            ViewModelProvider(
                this, viewModelFactory
            )[DashboardViewModel::class.java]
        attachListener()


        return fragmentTaskDetailsBinding.root

    }


    private fun attachListener() {
        fragmentTaskDetailsBinding.button.setOnClickListener {
            task.status = spinner.selectedItem.toString()
            dashboardViewModel.updateTaskItem(task)
            requireActivity().onBackPressed()
        }
    }
}