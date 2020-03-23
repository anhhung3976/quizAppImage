package com.example.quizappimage.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.quizappimage.Model.Category_detail
import com.example.quizappimage.QuestionActivity
import com.example.quizappimage.R
import com.example.quizappimage.`interface`.IOnRecyclerViewItemClickListener1
import com.example.quizappimage.common.Common1



class CategoryAdapter1(internal var context: Context, internal var categoryDetailList:List<Category_detail>):

    RecyclerView.Adapter<CategoryAdapter1.MyViewHolder1>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder1 {
        val itemView = LayoutInflater.from(context).inflate(R.layout.activity_detail_1_category,parent,false)
        return MyViewHolder1(itemView)
    }

    override fun getItemCount(): Int {
        return categoryDetailList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder1, position: Int) {
        holder.txt_category_detail_1_name.text = categoryDetailList[position].name
        holder.setiOnRecyclerViewItemClickListener1(object: IOnRecyclerViewItemClickListener1 {
            override fun onClick(view: View, position: Int) {
                Common1.selectedCategoryId = categoryDetailList[position]
                val intent = Intent(context, QuestionActivity::class.java)
                context.startActivity(intent)

            }
        })

    }

    inner class MyViewHolder1(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{

        internal val txt_category_detail_1_name : TextView
        internal val card_category_detail_1 : CardView
        internal lateinit var iOnRecyclerViewItemClickListener1: IOnRecyclerViewItemClickListener1

        fun setiOnRecyclerViewItemClickListener1(iOnRecyclerViewItemClickListener1: IOnRecyclerViewItemClickListener1) {
            this.iOnRecyclerViewItemClickListener1 = iOnRecyclerViewItemClickListener1

        }

        init {
            txt_category_detail_1_name = itemView.findViewById(R.id.txt_category_detail_1_name) as TextView
            card_category_detail_1 =    itemView.findViewById(R.id.card_category_detail_1) as CardView
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            iOnRecyclerViewItemClickListener1.onClick(view,adapterPosition)

        }

    }

}