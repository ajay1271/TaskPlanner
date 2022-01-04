package com.ajayspace.taskplanner

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider

import com.ajayspace.taskplanner.database.DashboardViewModelFactory
import com.ajayspace.taskplanner.database.TasksDatabase
import com.ajayspace.taskplanner.database.TodoItem
import com.ajayspace.taskplanner.databinding.FragmentAddItemBinding


class AddItemFragment : Fragment() {

    private lateinit var fragmentAddItemBinding: FragmentAddItemBinding
    //private val dashboardViewModel: DashboardViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentAddItemBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_add_item, container, false)

        val application = requireNotNull(this.activity).application

        val datasource = TasksDatabase.getInstance(application).taskDao


        val viewModelFactory = DashboardViewModelFactory(datasource, application)


        val dashboardViewModel =
            ViewModelProvider(
                this, viewModelFactory
            )[DashboardViewModel::class.java]

        val spinner: Spinner = fragmentAddItemBinding.prioritySpinner
        activity?.let {
            ArrayAdapter.createFromResource(
                it.applicationContext,
                R.array.priorities_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }
        }



        fragmentAddItemBinding.button.setOnClickListener {

            var title: String = fragmentAddItemBinding.tlTitle.text.toString()
            var desc: String = fragmentAddItemBinding.desc.text.toString()
            var priority = spinner.selectedItem.toString()
            if (checkEmptyFields(title, desc, priority)) {
                dashboardViewModel.isInCompletedClicked.value = true;
                dashboardViewModel.isCompletedClicked.value = false;
                dashboardViewModel.isProgressClicked.value = false;
                Log.i("sharedViewmodel", "init in AddItem --- > ${dashboardViewModel.initAlready}")
                dashboardViewModel.addTask(TodoItem(title = title, desc = desc, priority = priority, isCompleted = false))
                dashboardViewModel.initAlready.value = false
                requireActivity().onBackPressed()
            }
        }
        return fragmentAddItemBinding.root
    }

    private fun checkEmptyFields(title: String, desc: String, priority: String): Boolean {
        if (title.isEmpty() || desc.isEmpty() || priority.isEmpty()) {
            Toast.makeText(activity, "Please enter all fields", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

}