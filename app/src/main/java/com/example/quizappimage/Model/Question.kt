package com.example.quizappimage.Model

class Question
//    (private var id: Int = 0,
//    var question: String? ,
//     var questionImage: String ,
//     var option1: String?,
//     var option2: String?,
//     var option3: String?,
//     var option4: String?,
//     var answerNr: String?,
//     var id_category: Int,
//     var isImageQuestion : Boolean,
//     var name: String? )

{
    private var id: Int = 0
    private var question: String? = null
    private var questionImage: String ?= null
    private var option1: String? = null
    private var option2: String? = null
    private var option3: String? = null
    private var option4: String? = null
    private var answerNr: String? = null
    private var id_category: Int = 0
    private var isImageQuestion : Boolean ?= null
    private var name: String? = null

    constructor()

    constructor(id: Int, question: String,questionImage : String,
                option1: String, option2: String, option3: String,
                option4: String, answerNr: String, id_category: Int,
                isImageQuestion: Boolean, name: String) {

        this.id = id
        this.question = question
        this.questionImage = questionImage
        this.option1 = option1
        this.option2 = option2
        this.option3 = option3
        this.option4 = option4
        this.answerNr = answerNr
        this.id_category = id_category
        this.isImageQuestion = isImageQuestion
        this.name = name
    }
    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getQuestion(): String {
        return question!!
    }

    fun setQuestion(question: String) {
        this.question = question
    }

    fun getQuestionImage() : String {
        return questionImage!!

    }

    fun setQuestionImage(questionImage: String) {
        this.questionImage = questionImage
    }
    fun getOption1(): String {
        return option1!!
    }

    fun setOption1(option1: String) {
        this.option1 = option1
    }

    fun getOption2(): String {
        return option2!!
    }

    fun setOption2(option2: String) {
        this.option2 = option2
    }

    fun getOption3(): String {
        return option3!!
    }

    fun setOption3(option3: String) {
        this.option3 = option3
    }

    fun getOption4(): String {
        return option4!!
    }

    fun setOption4(option4: String) {
        this.option4 = option4
    }

    fun getAnswerNr(): String {
        return answerNr!!
    }

    fun setAnswerNr(answerNr: String) {
        this.answerNr = answerNr
    }

    fun getId_Category(): Int {
        return id_category
    }

    fun setId_Category(id_category: Int) {
        this.id_category = id_category
    }

    fun getIsImageQuestion() : Boolean{
        return isImageQuestion!!
    }

    fun setIsImageQuestion(isImageQuestion: Boolean) {
        this.isImageQuestion=isImageQuestion
    }

    fun getName(): String {
        return name!!
    }

    fun setName(name: String) {
        this.name = name
    }

}