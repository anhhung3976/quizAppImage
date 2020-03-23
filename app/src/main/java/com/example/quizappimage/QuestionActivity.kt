package com.example.quizappimage

import android.content.Intent
import android.graphics.Color
import android.hardware.Camera
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.quizappimage.DBhelper.DBHelperOther
import com.example.quizappimage.Model.Question
import com.example.quizappimage.common.Common1
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_question.*
import java.lang.Exception
import java.util.ArrayList

class QuestionActivity : AppCompatActivity(), View.OnClickListener {


    private lateinit var layout_image : FrameLayout
    private lateinit var imageView :ImageView

    private var clickedButton: Button? = null
    private var btn1: Button? = null
    private var btn2: Button? = null
    private var btn3: Button? = null
    private var btn4: Button? = null
    private var buttonConfirmNext: Button? = null

    private var textViewQuestion: TextView? = null//text view câu hỏi
    private var textViewScore: TextView? = null//text view  điểm
    private var textViewQuestionCount: TextView? = null//text view số câu hiện tại
    private var textViewCountDown: TextView? = null//text view tổng số câu hỏi
    private var textViewCorrect: TextView? = null//text view câu đúng
    private var textViewWrong: TextView? = null//text view câu sai

    private var questionsList: ArrayList<Question>? = null//danh sách câu hỏi
    private var questionCounter: Int = 0//số câu đã trả lời
    private var questionTotalCount: Int = 0//tổng số câu hỏi
    private var currentQuestion: Question? = null
    private var answer: Boolean = false

    private val handler = Handler()
    private var correctAns : Int  = 0
    private var wrongAns : Int  = 0
    var score: Int =0
    private var index : Int =0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        //ánh xạ
        setupUI()

        //hàm lấy danh sách câu hỏi theo lớp đã chọn
        genQuestion()

    }

    private fun setupUI() {

        layout_image = findViewById(R.id.layout_image)


        textViewQuestion = findViewById(R.id.txtQuestionContainer)
        textViewCorrect = findViewById(R.id.txtCorrect)
        textViewWrong = findViewById(R.id.txtWrong)
        textViewCountDown = findViewById(R.id.txtViewTimer)
        textViewQuestionCount = findViewById(R.id.txtTotalQuestion)
        textViewScore = findViewById(R.id.txtScore)

        buttonConfirmNext = findViewById(R.id.button)
        btn1 = findViewById(R.id.btn1)
        btn2 = findViewById(R.id.btn2)
        btn3 = findViewById(R.id.btn3)
        btn4 = findViewById(R.id.btn4)


    }

    //Lúc khởi chạy lần đầu: genQuestion->startQuiz->showQuestion->quizOperation->checkSolution->showQuestion
    //Sau khởi chạy và chọn đúng showQuestion->quizOperation->checkSolution->showQuestion
    //Sau khởi chạy và chọn sai  showQuestion->quizOperation->checkSolution->changeIncorrectColor-> showQuestion
    //tiếp tục như thế cho tới khi hết tổng câu hỏi thì dừng

    private fun genQuestion() {
        //lấy danh sách câu hỏi dựa theo lớp đã chọn
        questionsList = DBHelperOther.getInstance(this).getAllQuestionByGrade(Common1.selectedCategoryId!!.id)

        //nếu không có câu hỏi nào của mục được chọn thì
        if(questionsList!!.size==0) {
            MaterialStyledDialog.Builder(this)
                .setTitle("Oops")
                .setIcon(R.drawable.ic_sentiment_very_dissatisfied_black_24dp)
                .setDescription("Don't have any Question in this ${Common1.selectedCategoryId!!.name} category")
                .setPositiveText("Ok")
                .onPositive{dialog, which ->
                    dialog.dismiss()
                    finish()

                }.show()
        }

        //bắt đầu chương trình
        if(questionsList!!.size !=0)
        startQuiz()
    }

    private fun startQuiz() {
        questionTotalCount = questionsList!!.size
//        Collections.shuffle(questionsList)//random câu hỏi

        showQuestions(index)
        if (!answer) {
            quizOperation()
        }//if


    }

    private fun showQuestions(index : Int) {
        btn1!!.setEnabled(true)
        btn2!!.setEnabled(true)
        btn3!!.setEnabled(true)
        btn4!!.setEnabled(true)

        btn1!!.setTextColor(Color.BLACK)
        btn2!!.setTextColor(Color.BLACK)
        btn3!!.setTextColor(Color.BLACK)
        btn4!!.setTextColor(Color.BLACK)

        btn1!!.setBackground(ContextCompat.getDrawable(applicationContext, R.drawable.button_background))
        btn2!!.setBackground(ContextCompat.getDrawable(applicationContext, R.drawable.button_background))
        btn3!!.setBackground(ContextCompat.getDrawable(applicationContext, R.drawable.button_background))
        btn4!!.setBackground(ContextCompat.getDrawable(applicationContext, R.drawable.button_background))


        //nếu số câu hỏi hiện tại < tổng số câu hỏi(VD 2(số câu đã trả lời)<6(tổng số câu hỏi))
        if (index < questionTotalCount) {
            currentQuestion = questionsList!!.get(questionCounter)
            textViewQuestion!!.setText(currentQuestion!!.getQuestion())


            if(currentQuestion!!.getIsImageQuestion()) {
                imageView = findViewById(R.id.img_question) as ImageView

//                val imageId = resources.getIdentifier(
//                    questionsList!!.get(index).getQuestionImage()?.toLowerCase(), "drawable",
//                    packageName
//                )
//                layout_image!!.setBackgroundResource(imageId)

                Picasso.get().load(currentQuestion!!.getQuestionImage()).into(imageView, object : com.squareup.picasso.Callback{
                    override fun onSuccess() {
//                        val imageId = resources.getIdentifier(
//                            currentQuestion!!.getQuestionImage().toLowerCase(), "drawable",
//                            packageName
//                        )
//
//                        imageView!!.setBackgroundResource(imageId)


                    }
                    override fun onError(e: Exception?) {
                        img_question.setImageResource(R.drawable.ic_sentiment_very_dissatisfied_black_24dp)
                    }
                })

            }

            else {
                layout_image!!.setVisibility(View.GONE)
            }

            btn1!!.setText(currentQuestion!!.getOption1())
            btn2!!.setText(currentQuestion!!.getOption2())
            btn3!!.setText(currentQuestion!!.getOption3())
            btn4!!.setText(currentQuestion!!.getOption4())

//            btn1!!.setText(currentQuestion!!.option1)
//            btn2!!.setText(currentQuestion!!.option2)
//            btn3!!.setText(currentQuestion!!.option3)
//            btn4!!.setText(currentQuestion!!.option4)

            questionCounter++
            answer = false //chưa chọn câu trả lời
            buttonConfirmNext!!.setText("Confirm")
            textViewQuestionCount!!.setText("Question :$questionCounter/$questionTotalCount")

        } else {

            //hàm này để không cho chương trình chạy quá nhanh mà ko thấy rõ hiệu ứng
            handler.postDelayed({
                finalResult()
            }, 1000)
        }
    }

    private fun quizOperation() {
        answer = true // đã trả lời câu hỏi(đã chọn câu trả lời)
        btn1!!.setOnClickListener(this)
        btn2!!.setOnClickListener(this)
        btn3!!.setOnClickListener(this)
        btn4!!.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        clickedButton = v as Button
        btn1!!.setEnabled(false)
        btn2!!.setEnabled(false)
        btn3!!.setEnabled(false)
        btn4!!.setEnabled(false)

        if (clickedButton!!.getText() == questionsList!!.get(index).getAnswerNr()) {
            clickedButton!!.setBackground(ContextCompat.getDrawable(applicationContext, R.drawable.when_answer_correct))
            correctAns++
            score += 10
            textViewScore!!.setText("Score: $score")
            textViewCorrect!!.setText("Correct: $correctAns")


            handler.postDelayed({
                clickedButton!!.setBackground(ContextCompat.getDrawable(applicationContext, R.drawable.button_background))
                showQuestions(++index)
            }, 2000)

        } else {

            if (btn1!!.getText() == questionsList!!.get(index).getAnswerNr()) {
                btn1!!.setBackground(ContextCompat.getDrawable(applicationContext, R.drawable.when_answer_correct))
            }

            if (btn2!!.getText() == questionsList!!.get(index).getAnswerNr()) {
                btn2!!.setBackground(ContextCompat.getDrawable(applicationContext, R.drawable.when_answer_correct))
            }

            if (btn3!!.getText() == questionsList!!.get(index).getAnswerNr()) {
                btn3!!.setBackground(ContextCompat.getDrawable(applicationContext, R.drawable.when_answer_correct))

            }

            if (btn4!!.getText() == questionsList!!.get(index).getAnswerNr()) {
                btn4!!.setBackground(ContextCompat.getDrawable(applicationContext, R.drawable.when_answer_correct))
            }

            changeIncorrectColor(clickedButton!!)
            wrongAns++
            textViewWrong!!.setText("Wrong:$wrongAns")
            clickedButton!!.setEnabled(false)

            handler.postDelayed({
                clickedButton!!.setBackground(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.button_background
                    )
                )
                showQuestions(++index)
            }, 2000)

        }
    }

    //nếu trả lời sai thì app dừng ngay và hiển thị điểm đã đạt được
    private fun changeIncorrectColor(btnSelected: Button) {
        btnSelected.setBackground(ContextCompat.getDrawable(this, R.drawable.when_answer_wrong))
        btnSelected.setTextColor(Color.WHITE)
    }

    //kết quả cuối cùng
    private fun finalResult() {
        val resultData = Intent(this@QuestionActivity, Result::class.java)
        resultData.putExtra("UserScore", score)
        resultData.putExtra("CorrectQues", correctAns)
        startActivity(resultData)
    }
}