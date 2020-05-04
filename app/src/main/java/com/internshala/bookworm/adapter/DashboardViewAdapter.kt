package com.internshala.bookworm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.internshala.bookworm.R
import com.internshala.bookworm.model.Book

class DashboardViewAdapter(private val dataList: ArrayList<Book>, val context: Context) :
    RecyclerView.Adapter<DashboardViewAdapter.DashboardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.singlerow_of_recycler_view_dashboard, parent, false)
        return DashboardViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        holder.bookName.text = dataList[position].book
        holder.author.text = dataList[position].bookAthuor
        holder.price.text = dataList[position].bookPrice
        holder.rating.text = dataList[position].bookRating
        /*holder.bookImage.setImageResource(dataList[position].bookImage)*/
        holder.parentLinearlayout.setOnClickListener()
        {
            Toast.makeText(context, "Clicked on ${dataList[position].book}", Toast.LENGTH_LONG)
                .show()
        }
    }

    class DashboardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var bookName: TextView = view.findViewById(R.id.txtBookName)
        var author: TextView = view.findViewById(R.id.txtBookAuthor)
        var price: TextView = view.findViewById(R.id.txtBookPrice)
        var rating: TextView = view.findViewById(R.id.txtBookRating)
        /*var bookImage: ImageView = view.findViewById(R.id.imgBookImage)*/
        var parentLinearlayout: LinearLayout = view.findViewById(R.id.parentLinearLayout)
    }


}