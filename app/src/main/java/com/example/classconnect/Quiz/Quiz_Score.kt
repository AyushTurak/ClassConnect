package com.example.classconnect.Quiz

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.classconnect.DashBoard.Student.stdashboard
import com.example.classconnect.R
import com.example.classconnect.databinding.ActivityQuizScoreBinding

class Quiz_Score : AppCompatActivity() {
    private lateinit var binding: ActivityQuizScoreBinding
    val handler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { v: View, insets: WindowInsetsCompat ->
            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // Retrieve the data from the intent
        val intent = intent
        val message = intent.getIntExtra("Score", 0)


        // Use the retrieved data as needed
        binding.scoreScore.setText("$message / 7")

        val home = findViewById<Button>(R.id.home_quiz)
        home.setOnClickListener {
            val xntent: Intent = Intent(
                this@Quiz_Score,
                stdashboard::class.java
            )
            startActivity(xntent)
            finish()
        }

        if (message == 7) {
            binding.imageView.setImageResource(R.drawable.thumbs)
        } else if (message < 7 && message > 4) {
            binding.imageView.setImageResource(R.drawable.ok)

        } else if (message > 0 && message <= 4) {
            binding.imageView.setImageResource(R.drawable.sad)
        } else {
            binding.imageView.setImageResource(R.drawable.sad)
        }
    }
}