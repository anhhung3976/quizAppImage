package com.example.quizappimage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.quizappimage.Adapter.CategoryAdapter
import com.example.quizappimage.DBhelper.DBHelperOther
import com.example.quizappimage.common.SplacesItemDecoration
import kotlinx.android.synthetic.main.activity_detail.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        recycler_detail.setHasFixedSize(true)// tối ưu hoá dữ liệu để ko bị ảnh hưởng bởi nội dung trong adapter

        //recyclerview được tổ chức theo gridlayout với 2 cột
        recycler_detail.layoutManager = GridLayoutManager(this, 2)

        val database = DBHelperOther(this)
//        database.deleteDataBase()
        database.createDataBase()//tạo database

        //categgory Adapter nhận 2 tham số contex và danh sách category
        val adapter = CategoryAdapter(this, DBHelperOther.getInstance(this).allCategory())

        recycler_detail.addItemDecoration(SplacesItemDecoration(4))

        //sau khi có adapter  ,đổ adapter vào recycle view
        recycler_detail.adapter = adapter
    }
}
