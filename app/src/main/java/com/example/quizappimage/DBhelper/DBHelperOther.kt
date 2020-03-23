package com.example.quizappimage.DBhelper

import android.annotation.TargetApi
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import com.example.quizappimage.Model.Category
import com.example.quizappimage.Model.Category_detail
import com.example.quizappimage.Model.Question

import java.io.*
import java.sql.SQLException
import java.util.ArrayList

class DBHelperOther :SQLiteOpenHelper {

    //biến static ,final và static final
    companion object {
        private var instance : DBHelperOther?= null
        const val DB_PATH: String = "/data/data/com.example.quizappimage/databases/"
        const val DB_NAME: String = "imageDB.db"
        const val DATABASE_VERSION: Int = 1
        var myContext: Context? = null

        @Synchronized
        fun getInstance(context: Context):DBHelperOther {
            if(instance==null)
                instance= DBHelperOther(context)
            return  instance!!
        }
    }

    private var myDataBase: SQLiteDatabase? = null

    constructor (context: Context):  super(context, DB_NAME, null, DATABASE_VERSION) {
        myContext =context
    }

    @Throws(SQLException::class)
    fun openDataBase() {
        //Open the database
        val myPath = DB_PATH + DB_NAME
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY)

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    fun deleteDataBase() {
        val myPath:String = DB_PATH + DB_NAME
        SQLiteDatabase.deleteDatabase(File(myPath))
    }

    @Synchronized
    override fun close() {
        if (myDataBase != null)
            myDataBase!!.close()
        super.close()
    }

    private fun checkDataBase(): Boolean {
        val dbFile = myContext!!.getDatabasePath(DB_NAME)
        return dbFile.exists()
    }


    @Throws(IOException::class)
    private fun copyDataBase() {

        //duong dan den db se tao
        val outFileName:String = DB_PATH + DB_NAME

        //mo db giong nhu mot output stream
        val myOutput: OutputStream = FileOutputStream(outFileName)

        //mo db trong thu muc assets nhu mot input stream
        val myInput: InputStream = myContext!!.assets.open(DB_NAME)

        //truyen du lieu tu inputfile sang outputfile
        val buffer = ByteArray(1024)
        var length: Int = myInput.read(buffer)

        while (length> 0) {
            myOutput.write(buffer, 0, length)
            length = myInput.read(buffer)
        }

        //Dong luon
        myInput.close()
        myOutput.flush()
        myOutput.close()
    }


    //tạo database
    @Throws(IOException::class)
    fun createDataBase() {
        val dbExist = checkDataBase()

        if (dbExist) {
            //khong lam gi ca, database da co roi
            //copyDataBase();
        } else {
            this.readableDatabase
            try {
                copyDataBase() //chep du lieu
            } catch (e: IOException) {
                throw Error("Error copying database")
            }

        }
    }

    //lấy category từ database
    fun allCategory():ArrayList<Category>{
        var listCategory   =  ArrayList<Category>()
        myDataBase =  readableDatabase
        var sql:String="SELECT * FROM category"

        //thực thi câu lệnh query trả về 1 bảng
        var cursor : Cursor = myDataBase!!.rawQuery(sql,null)

        //duyệt từ dòng đầu tiên của bảng
        if(cursor.moveToFirst()) {
            do {
                val category: Category
                category = Category(
                    cursor.getInt(0),
                    cursor.getString(1)
                )
                listCategory.add(category)//add question vào mảng listData
            } while (cursor.moveToNext())

        }
        cursor.close()
        return listCategory

    }


    fun allCategoryDetail1(idCategory: Int):ArrayList<Category_detail>{
        var listCategoryDetail   =  ArrayList<Category_detail>()
        myDataBase =  readableDatabase
        var sql:String="SELECT * FROM category_detail where idCategory = $idCategory"

        //thực thi câu lệnh query trả về 1 bảng
        var cursor : Cursor = myDataBase!!.rawQuery(sql,null)

        //duyệt từ dòng đầu tiên của bảng
        if(cursor.moveToFirst()) {
            do {
                val categoryDetail: Category_detail
                categoryDetail = Category_detail(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2)

                )
                listCategoryDetail.add(categoryDetail)//add question vào mảng listData
            } while (cursor.moveToNext())

        }
        cursor.close()
        return listCategoryDetail

    }


    //lấy danh sách câu hỏi từ database theo category được chọn
    fun getAllQuestionByGrade(id_category: Int): ArrayList<Question> {
        var listData   =  ArrayList<Question>()
        myDataBase =  readableDatabase
//        var sql:String= "select * from quiz_question_kotlin_test_doidb where Option5= '"+ option5+"'and Option6 = '" + option6+"'"
        var sql:String= "select * from quiz_question_kotlin_test_doidb where id_category= $id_category order by Random()"

        //thực thi câu lệnh query trả về 1 bảng
        var cursor : Cursor = myDataBase!!.rawQuery(sql,null)


        //duyệt từ dòng đầu tiên của bảng
        if(cursor.moveToFirst()) {
            do {
                val question: Question
                question = Question(
                    cursor.getInt(0),//id
                    cursor.getString(1),//ques
                    cursor.getString(2),//quesImage
                    cursor.getString(3),//option1
                    cursor.getString(3),//option2
                    cursor.getString(4),//option3
                    cursor.getString(5),//option4
                    cursor.getString(6),//answer
                    cursor.getInt(7),//id_category
                    if(cursor.getInt(8) ==0) java.lang.Boolean.FALSE else java.lang.Boolean.TRUE,
                    cursor.getString(9)
                )

                listData.add(question)//add question vào mảng listData
            } while (cursor.moveToNext())

        }
        cursor.close()
        return listData

    }

//    //lấy danh sách câu hỏi từ database theo category được chọn
//    fun getAllQuestionByGrade(id_category: Int): ArrayList<Question> {
//        var listData   =  ArrayList<Question>()
//        myDataBase =  readableDatabase
////        var sql:String= "select * from quiz_question_kotlin_test_doidb where Option5= '"+ option5+"'and Option6 = '" + option6+"'"
//        var sql:String= "select * from quiz_question_kotlin_test_doidb where id_category= $id_category order by Random()"
//
//        //thực thi câu lệnh query trả về 1 bảng
//        var cursor : Cursor = myDataBase!!.rawQuery(sql,null)
//
//
//        //duyệt từ dòng đầu tiên của bảng
//        if(cursor.moveToFirst()) {
//            do {
//                val question: Question
//                question = Question(
//                    cursor.getInt(cursor.getColumnIndex("id")),//id
//                    cursor.getString(cursor.getColumnIndex("id")),//ques
//                    cursor.getString(cursor.getColumnIndex("id")),//quesimage
//                    cursor.getString(cursor.getColumnIndex("id")),//option1
//                    cursor.getString(cursor.getColumnIndex("id")),//optiion2
//                    cursor.getString(cursor.getColumnIndex("id")),//optiion3
//                    cursor.getString(cursor.getColumnIndex("id")),//optiion4
//                    cursor.getString(cursor.getColumnIndex("id")),//answer
//                    cursor.getInt(cursor.getColumnIndex("id")),//id
//                    if(cursor.getInt(cursor.getColumnIndex("IsImageQuestion"))==0) java.lang.Boolean.FALSE else java.lang.Boolean.TRUE,
//                     cursor.getString(cursor.getColumnIndex("id"))
//
//                )
//                listData.add(question)//add question vào mảng listData
//            } while (cursor.moveToNext())
//
//        }
//        cursor.close()
//        return listData
//
//    }

    override fun onCreate(db: SQLiteDatabase?) {

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}