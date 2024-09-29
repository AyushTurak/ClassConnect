package com.example.classconnect.Attendance_Dashboard

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.classconnect.DataClasses.DateItem
import com.example.classconnect.DateAdapter
import com.example.classconnect.R

class Attendance : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_attendance)

        val recyclerView: RecyclerView = findViewById(R.id.dateRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Sample data (dates and students)
        val dateList = listOf(
            DateItem("2024-09-01", listOf("John Doe", "Jane Smith", "Alex Johnson")),
            DateItem("2024-09-02", listOf("Sarah Lee", "Robert Brown", "Emma Davis")),
            DateItem("2024-09-03", listOf("Mike Wilson", "Sophia Clark", "James Taylor"))
        )

        // Set the adapter
        val adapter = DateAdapter(dateList)
        recyclerView.adapter = adapter

    }
}