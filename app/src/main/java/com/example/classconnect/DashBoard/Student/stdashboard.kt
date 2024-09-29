package com.example.classconnect.DashBoard.Student

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.classconnect.Attendance_Dashboard.Attendance
import com.example.classconnect.Progress.ProgressActivity
import com.example.classconnect.R
import com.example.classconnect.databinding.ActivityAttendanceBinding
import com.example.classconnect.databinding.ActivityLoginBinding
import com.example.classconnect.databinding.ActivityStdashboardBinding

class stdashboard : AppCompatActivity() {

    private lateinit var binding: ActivityStdashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStdashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

       binding.attendenceBtn.setOnClickListener {
           val intent = Intent(this,  Attendance::class.java) // Navigate to the main app

           startActivity(intent)
       }
       binding.button6.setOnClickListener {
           val intent = Intent(this,ProgressActivity::class.java)
           startActivity(intent)
       }


    }
}