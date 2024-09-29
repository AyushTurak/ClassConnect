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

    private var quizQuestions: ArrayList<Question> = arrayListOf()
    private var mediaPlayer: MediaPlayer? = null
    private var score: Int = 0
    private var qNo: Int = 0
    private var qScore: TextView? = null
    private var img: ImageView? = null
    private var question: TextView? = null
    private var answer: EditText? = null
    private var handler: Handler? = null
    private lateinit var binding: ActivityQuizBinding
    private var currentQuestionIndex: Int = 0
    private var countDownTimer: CountDownTimer? = null
    private var tvTimer: TextView? = null
    private val startTimeInMillis: Long = 15000 // 15 seconds in milliseconds
    private var timeLeftInMillis = startTimeInMillis // Need to track time left
    private var timerRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handler = Handler(Looper.getMainLooper())

        qScore = binding.scoreQuiz
        score = 0

        // Initialize views
        question = binding.quizQuestionText
        answer = binding.quizAnswerText
        img = binding.changeImage
        tvTimer = binding.timerQuiz
        val enter = binding.enterBtnQuiz

        // Initialize quiz questions
        initializeQuestions()

        // Display the first question
        displayQuestion()

        // Button click listener
        enter.setOnClickListener { checkAnswer() }
    }

    private fun initializeQuestions() {
        quizQuestions.add(Question("What is 2 + 2?", "4"))
        quizQuestions.add(Question("What is 5 - 3?", "2"))
        quizQuestions.add(Question("What is 4 × 6?", "24"))
        quizQuestions.add(Question("What is 15 ÷ 3?", "5"))
        quizQuestions.add(Question("What is 8 + 7?", "15"))
        quizQuestions.add(Question("What is 10 - 4?", "6"))
        quizQuestions.add(Question("What is 3 × 5?", "15"))
        quizQuestions.add(Question("What is 18 ÷ 2?", "9"))
        quizQuestions.add(Question("What is 9 + 3?", "12"))
        quizQuestions.add(Question("What is 20 - 11?", "9"))
    }

    private fun startTimer() {
        countDownTimer?.cancel()

        countDownTimer = object : CountDownTimer(startTimeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateTimer()
            }

            override fun onFinish() {
                tvTimer?.text = "Timer finished!"
                // Handle time-out situation here if needed
            }
        }.start()
    }

    private fun updateTimer() {
        val seconds = (timeLeftInMillis / 1000).toInt()
        tvTimer?.text = "Time left: $seconds seconds"
    }

    private fun displayQuestion() {
        answer?.setText("")
        img?.setImageResource(R.drawable.quiz_que)

        // Check if it's the last question
        if (qNo >= quizQuestions.size) {
            val intent = Intent(this, Quiz_Score::class.java)
            intent.putExtra("Score", score)
            startActivity(intent)
            finish()
        } else {
            qNo++
            currentQuestionIndex = (currentQuestionIndex + 1) % quizQuestions.size
            val currentQuestion = quizQuestions[currentQuestionIndex]

            // Directly access the question without explicit getter
            question?.text = currentQuestion.question
            startTimer()
        }
    }


    private fun checkAnswer() {
        if (currentQuestionIndex in 0 until quizQuestions.size) {
            val currentQuestion = quizQuestions[currentQuestionIndex]
            val userAnswer = answer?.text.toString()

            if (currentQuestion.answer == userAnswer) {
                score++
                mediaPlayer?.start() // Start media playback on correct answer
                img?.setImageResource(R.drawable.quiz_win)
            } else {
                score--
                mediaPlayer?.start() // Handle wrong answer sound
                img?.setImageResource(R.drawable.quiz_lose)
            }

            qScore?.text = "Score : $score"

            handler?.postDelayed({
                currentQuestionIndex++
                displayQuestion()
            }, 2000)
        }
    }
}
