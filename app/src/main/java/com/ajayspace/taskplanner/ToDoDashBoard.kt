package com.ajayspace.taskplanner

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ajayspace.taskplanner.database.DashboardViewModelFactory
import com.ajayspace.taskplanner.database.TasksDatabase
import com.ajayspace.taskplanner.databinding.FragmentToDoDashBoardBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*


class ToDoDashBoard : Fragment() {

    private lateinit var fragmentTodoDashBoardBinding: FragmentToDoDashBoardBinding
    private lateinit var dashboardViewModel: DashboardViewModel;
    private lateinit var recylerView: RecyclerView
    private lateinit var INCOMPLETE: String;
    private lateinit var IN_PROGRESS: String;
    private lateinit var COMPLETE: String;


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("fx", "onCreate-->")
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val application = requireNotNull(this.activity).application
        val datasource = TasksDatabase.getInstance(application).taskDao
        val viewModelFactory = DashboardViewModelFactory(datasource, application)
        dashboardViewModel =
            ViewModelProvider(
                this, viewModelFactory
            )[DashboardViewModel::class.java]

        //Getting values
        val stringArray = resources.getStringArray(R.array.task_status_array);

        INCOMPLETE = stringArray[0]
        IN_PROGRESS = stringArray[1]
        COMPLETE = stringArray[2]

        Log.i("fx", "onCreateView-->")

        Log.i("sharedViewmodel", "Before--> ${dashboardViewModel.data}")
        dashboardViewModel.data = dashboardViewModel.data + 5
        Log.i("sharedViewmodel", "after--> ${dashboardViewModel.data}")


        //Data binding
        fragmentTodoDashBoardBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_to_do_dash_board, container, false)

        //fields
        val text: TextView = fragmentTodoDashBoardBinding.hello
        val button: FloatingActionButton = fragmentTodoDashBoardBinding.button
        button.setOnClickListener {
            handleButtonClick(it)
        }

        fragmentTodoDashBoardBinding.incompleteSelected.setOnClickListener {

            Log.i("fx", "incompleteSelected.setOnClickListener -->")
            setButton(INCOMPLETE)
        }
        fragmentTodoDashBoardBinding.completedSelected.setOnClickListener {

            Log.i("fx", "completedSelected.setOnClickListener -->")
            setButton(COMPLETE)
        }
        fragmentTodoDashBoardBinding.inProgressSelected.setOnClickListener {
            Log.i("fx", "inProgressSelected.setOnClickListener-->")
            setButton(IN_PROGRESS)
        }
        attachObserver()


        Log.i("sharedViewmodel", "initialized--> with init--> ${dashboardViewModel.initAlready}")
        //RecyclerView
        recylerView = fragmentTodoDashBoardBinding.recyclerView
        recylerView.layoutManager = LinearLayoutManager(activity)
        if (!dashboardViewModel.initAlready.value!!) {
            setButton(INCOMPLETE)
        }
        dashboardViewModel.initAlready.value = true


        return fragmentTodoDashBoardBinding.root
    }


    private fun attachObserver() {
        dashboardViewModel.tasksData.observe(viewLifecycleOwner, Observer { newTask ->
            recylerView.visibility = VISIBLE
            if (dashboardViewModel.tasksList.isNotEmpty()) {
                fragmentTodoDashBoardBinding.hello.visibility = GONE
            } else {
                fragmentTodoDashBoardBinding.hello.visibility = VISIBLE
            }
            Log.i("sharedViewmodel", "ToDoDashBoard.attachObserver()-->")
            recylerView.adapter =
                TodoAdapter(dashboardViewModel.tasksList, fragmentTodoDashBoardBinding)
        })
    }

    private fun handleButtonClick(view: View) {
        view.findNavController().navigate(R.id.action_toDoDashBoard_to_addItemFragment)


    }

    private fun setButton(s: String) {
        if (s == INCOMPLETE) {
            Log.i("sharedViewmodel", "INCOMPLETE-->called")
            recylerView.visibility = GONE
            fragmentTodoDashBoardBinding.inProgressSelected.setCardBackgroundColor(
                resources.getColor(
                    R.color.dark_grey_foreground
                )
            )
            fragmentTodoDashBoardBinding.completedSelected.setCardBackgroundColor(
                resources.getColor(
                    R.color.dark_grey_foreground
                )
            )
            fragmentTodoDashBoardBinding.incompleteSelected.setCardBackgroundColor(
                resources.getColor(
                    R.color.teal_700
                )
            )
            dashboardViewModel.isInCompletedClicked.value = true
            dashboardViewModel.isProgressClicked.value = false
            dashboardViewModel.isCompletedClicked.value = false
            dashboardViewModel.getTasksWithStatus("Incomplete")

        }

        if (s == COMPLETE) {
            Log.i("sharedViewmodel", "COMPLETE-->called")
            recylerView.visibility = GONE
            dashboardViewModel.isInCompletedClicked.value = false
            dashboardViewModel.isProgressClicked.value = false
            dashboardViewModel.isCompletedClicked.value = true
            fragmentTodoDashBoardBinding.inProgressSelected.setCardBackgroundColor(
                resources.getColor(
                    R.color.dark_grey_foreground
                )
            )
            fragmentTodoDashBoardBinding.completedSelected.setCardBackgroundColor(
                resources.getColor(
                    R.color.teal_700
                )
            )
            fragmentTodoDashBoardBinding.incompleteSelected.setCardBackgroundColor(
                resources.getColor(
                    R.color.dark_grey_foreground
                )
            )
            dashboardViewModel.getTasksWithStatus("Completed")
        }
        if (s == IN_PROGRESS) {
            Log.i("sharedViewmodel", "IN_PROGRESS-->called")
            recylerView.visibility = GONE
            dashboardViewModel.isInCompletedClicked.value = false
            dashboardViewModel.isProgressClicked.value = true
            dashboardViewModel.isCompletedClicked.value = false
            fragmentTodoDashBoardBinding.inProgressSelected.setCardBackgroundColor(
                resources.getColor(
                    R.color.teal_700
                )
            )
            fragmentTodoDashBoardBinding.completedSelected.setCardBackgroundColor(
                resources.getColor(
                    R.color.dark_grey_foreground
                )
            )
            fragmentTodoDashBoardBinding.incompleteSelected.setCardBackgroundColor(
                resources.getColor(
                    R.color.dark_grey_foreground
                )
            )
            dashboardViewModel.getTasksWithStatus("InProgress")
        }
    }

    override fun onResume() {
        super.onResume()
        Log.i("sharedViewmodel", "on resume clicked -->")
        if (dashboardViewModel.isCompletedClicked.value == true) {
            setButton(COMPLETE)
        }

        if (dashboardViewModel.isInCompletedClicked.value == true) {
            setButton(INCOMPLETE)
        }

        if (dashboardViewModel.isProgressClicked.value == true) {
            setButton(IN_PROGRESS)
        }
    }

}



