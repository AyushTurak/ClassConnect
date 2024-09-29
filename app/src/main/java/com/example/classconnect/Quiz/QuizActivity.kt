package com.example.classconnect.Quiz

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.classconnect.R
import com.example.classconnect.databinding.ActivityQuizBinding

class QuizActivity : AppCompatActivity() {

    var quizQuestions: ArrayList<Question>? = null
    private var mediaPlayer: MediaPlayer? = null
    var Score: Int = 0
    var qNo: Int = 0
    var Qscore: TextView? = null
    var img: ImageView? = null
    var question: TextView? = null
    var answer: EditText? = null
    var handler: Handler? = null
    private lateinit var binding: ActivityQuizBinding
    var currentQuestionIndex: Int = 0
    private var countDownTimer: CountDownTimer? = null
    private var tvTimer: TextView? = null
    private val startTimeInMillis: Long = 15000 // 15 seconds in milliseconds
    private val timeLeftInMillis = startTimeInMillis
    private val timerRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handler = Handler(Looper.getMainLooper())
        Qscore = binding.scoreQuiz
        Score = 0


        //        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        View view = binding.getRoot();
//        setContentView(view);

        // Initialize views
        question = binding.quizQuestionText
        answer = binding.quizAnswerText
        img = binding.changeImage
        tvTimer = binding.timerQuiz
        val enter = findViewById<Button>(R.id.enter_btn_quiz)
        qNo = 0


        // Initialize quiz questions
        quizQuestions = java.util.ArrayList<Question>()
        quizQuestions!!.add(Question("What is 2 + 2?", "4"))
        quizQuestions!!.add(Question("What is 5 - 3?", "2"))
        quizQuestions!!.add(Question("What is 4 × 6?", "24"))
        quizQuestions!!.add(Question("What is 15 ÷ 3?", "5"))
        quizQuestions!!.add(Question("What is 8 + 7?", "15"))
        quizQuestions!!.add(Question("What is 10 - 4?", "6"))
        quizQuestions!!.add(Question("What is 3 × 5?", "15"))
        quizQuestions!!.add(Question("What is 18 ÷ 2?", "9"))
        quizQuestions!!.add(Question("What is 9 + 3?", "12"))
        quizQuestions!!.add(Question("What is 20 - 11?", "9"))


        // Display first question
        displayQuestion()

        // Button click listener
        enter.setOnClickListener { checkAnswer() }


    }

    private fun startTimer() {
        countDownTimer?.cancel()

        // Create a new CountDownTimer for 15 seconds
        countDownTimer = object : CountDownTimer(15000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Update the timerTextView with the remaining time
                tvTimer!!.text = "Time remaining: " + millisUntilFinished / 1000 + " seconds"
            }

            override fun onFinish() {
                // Timer finished, do something here if needed
                tvTimer!!.text = "Timer finished!"
            }
        }

        // Start the timer
        (countDownTimer as CountDownTimer).start()
    }

    private fun updateTimer() {
        val seconds = (timeLeftInMillis / 1000).toInt()
        val timeLeftFormatted = String.format("%02d", seconds)
        tvTimer!!.text = "Time left: $timeLeftFormatted"
    }


    private fun displayQuestion() {
        answer!!.setText("")
        img!!.setImageResource(R.drawable.quiz_que)
        qNo++
        if (qNo == quizQuestions!!.size - 2) {
// Create an intent
            val intent = Intent(this, Quiz_Score::class.java)
            // Put extra data into the intent
            intent.putExtra("Score", Score)
            // Start the destination activity
            startActivity(intent)
            finish()
        }
        startTimer()
        // Check if currentQuestionIndex is within bounds
        if (currentQuestionIndex >= 0 && currentQuestionIndex < quizQuestions!!.size) {
            // Get the current question
            val currentQuestion: Question = quizQuestions!![currentQuestionIndex]
            // Set the question text
            question?.setText(currentQuestion.getQuestion())
        } else {
            // Reset text if no more questions
            question!!.text = ""
        }
    }

    private fun checkAnswer() {
        // Check if currentQuestionIndex is within bounds
        if (currentQuestionIndex >= 0 && currentQuestionIndex < quizQuestions!!.size) {
            // Get the current question
            val currentQuestion: Question = quizQuestions!![currentQuestionIndex]
            // Get the user's answer
            val userAnswer = answer!!.text.toString()
            // Check if the answer is correct
            if (currentQuestion.getAnswer().equals(userAnswer)) {
                // Start playback
                mediaPlayer!!.start()
                // Set win image
                Score++
                Qscore!!.text = "Score : $Score"
                img!!.setImageResource(R.drawable.quiz_win)
                handler!!.postDelayed({ // Code to be executed after 5 seconds
                    // This code will run on the main thread
                    // Do whatever you need to do after the delay
                    currentQuestionIndex++
                    // Display the next question
                    displayQuestion()
                }, 2000)
            } else {
                // Set lose image
                Score--
                Qscore!!.text = "Score : $Score"
                img!!.setImageResource(R.drawable.quiz_lose)

                // Start playback
                mediaPlayer!!.start()
                handler!!.postDelayed({ // Code to be executed after 5 seconds
                    // This code will run on the main thread
                    // Do whatever you need to do after the delay
                    currentQuestionIndex++
                    // Display the next question
                    displayQuestion()
                }, 2000)
            }

            // Move to the next question
        }
    }
}
