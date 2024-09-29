package com.example.classconnect

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.classconnect.DataClasses.DateItem
import com.example.classconnect.R

class DateAdapter(private val dateList: List<DateItem>) : RecyclerView.Adapter<DateAdapter.DateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.date_item, parent, false)
        return DateViewHolder(view)
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        val dateItem = dateList[position]
        holder.bind(dateItem)

        // Expand/Collapse student list on date click
        holder.dateTextView.setOnClickListener {
            dateItem.isExpanded = !dateItem.isExpanded
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int = dateList.size

    inner class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        private val studentCheckboxContainer: ViewGroup = itemView.findViewById(R.id.studentCheckboxContainer)

        fun bind(dateItem: DateItem) {
            dateTextView.text = dateItem.date

            // Clear existing views to prevent duplicates
            studentCheckboxContainer.removeAllViews()

            // Add student checkboxes dynamically
            dateItem.students.forEach { studentName ->
                val checkBox = CheckBox(itemView.context).apply {
                    text = studentName
                }
                studentCheckboxContainer.addView(checkBox)
            }

            // Show or hide the student list based on expansion state
            studentCheckboxContainer.visibility = if (dateItem.isExpanded) View.VISIBLE else View.GONE
        }
    }
}
