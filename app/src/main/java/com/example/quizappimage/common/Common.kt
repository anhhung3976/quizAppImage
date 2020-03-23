package com.example.quizappimage.common


import com.example.quizappimage.Model.Category

object Common {


    var selectedCategory:Category ? = null
    var selectedCategoryString:Category ? = null


    enum class ANSWER_TYPE{
        RIGHT_ANSWER,
        WRONG_ANSWER
    }
}