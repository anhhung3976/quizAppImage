package com.example.quizappimage.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.quizappimage.MainActitvity1
import com.example.quizappimage.Model.Category
import com.example.quizappimage.R
import com.example.quizappimage.`interface`.IOnRecyclerViewItemClickListener
import com.example.quizappimage.common.Common


class CategoryAdapter(internal var context: Context, internal var categoryList:List<Category>):

        RecyclerView.Adapter<CategoryAdapter.MyViewHolder>(){

    //hàm tạo view cho ViewHolder(ánh xạ layout cho viewHolder)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.activity_detail_category,parent,false)
        return MyViewHolder(itemView)
    }

    //lấy tổng danh sách category(theo DB =3)
    override fun getItemCount(): Int {
        return categoryList.size
    }

    //Phương thức này dùng để gắn data và view.
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.txt_category_detail_name.text = categoryList[position].name//lấy tên category dựa theo position hiện tại cho textView
        holder.setiOnRecyclerViewItemClickListener(object: IOnRecyclerViewItemClickListener {
            override fun onClick(view: View, position: Int) {
                    Common.selectedCategory = categoryList[position]
                    val intent = Intent(context, MainActitvity1::class.java)
                    context.startActivity(intent)

            }
        })
    }

    inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView),View.OnClickListener{
        //dùng viewholdder để giữ lại(nhanh hơn)

        internal val txt_category_detail_name : TextView
        internal val card_category : CardView
        internal lateinit var iOnRecyclerViewItemClickListener:IOnRecyclerViewItemClickListener

        fun setiOnRecyclerViewItemClickListener(iOnRecyclerViewItemClickListener: IOnRecyclerViewItemClickListener) {
            this.iOnRecyclerViewItemClickListener = iOnRecyclerViewItemClickListener

        }

        init {
            txt_category_detail_name = itemView.findViewById(R.id.txt_category_detail_name) as TextView
            card_category =    itemView.findViewById(R.id.card_category_detail) as CardView
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
        iOnRecyclerViewItemClickListener.onClick(view,adapterPosition)

        }

    }

}