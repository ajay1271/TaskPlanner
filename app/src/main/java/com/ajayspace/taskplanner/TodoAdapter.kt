package com.ajayspace.taskplanner

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ajayspace.taskplanner.database.TodoItem
import com.ajayspace.taskplanner.databinding.FragmentToDoDashBoardBinding

class TodoAdapter(
    private var todoItemsList: List<TodoItem>,
    fragmentToDoDashBoardBinding: FragmentToDoDashBoardBinding
) : RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleText: TextView = itemView.findViewById(R.id.title)
        val priorityText: TextView = itemView.findViewById(R.id.priority)
        val priorityIcon: CardView = itemView.findViewById(R.id.priority_icon)
        val card: CardView = itemView.findViewById(R.id.main_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.todo_item, parent, false)

        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return todoItemsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.card.setOnClickListener {

            var task = todoItemsList[position]
            it.findNavController()
                .navigate(ToDoDashBoardDirections.actionToDoDashBoardToTaskDetails(task))
        }
        holder.titleText.text = todoItemsList?.get(position)?.title
        val priority = todoItemsList?.get(position)?.priority.toString()
        holder.priorityText.text = priority

        Log.i("Color", priority)


        if (priority == "Low") {
            Log.i("Color", "working")
            holder.priorityIcon.setBackgroundColor(Color.BLUE)
        }
        if (priority == "Medium") {
            holder.priorityIcon.setBackgroundColor(Color.GREEN)
        }
        if (priority == "High") {
            holder.priorityIcon.setBackgroundColor(Color.RED)
        }

    }

}
