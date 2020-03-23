package com.example.quizappimage

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Result : AppCompatActivity() {
    var txtCorrectQues: TextView? = null
    var txtHighScore: TextView? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitivity_result)

        txtHighScore = findViewById(R.id.txtScore)
        txtCorrectQues = findViewById(R.id.result_Correct_Ques)



        val intent = intent
        val score = intent.getIntExtra("UserScore", 0)
        val correctQues = intent.getIntExtra("CorrectQues", 0)

        txtHighScore!!.setText("High score: $score ")
        txtCorrectQues!!.setText("Correct: $correctQues")

    }


}