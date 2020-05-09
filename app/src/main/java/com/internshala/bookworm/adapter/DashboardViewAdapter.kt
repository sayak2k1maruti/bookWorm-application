package com.internshala.bookworm.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.internshala.bookworm.R
import com.internshala.bookworm.activities.Description
import com.internshala.bookworm.model.Book
import com.squareup.picasso.Picasso

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
        Picasso.get().load(dataList[position].bookImage).error(R.drawable.default_book_cover).into(holder.bookImage)
        holder.parentLinearlayout.setOnClickListener()
        {
           val goToDescription = Intent(context,Description::class.java)
            goToDescription.putExtra("bookId",dataList[position].bookId)
            context.startActivity(goToDescription)
        }
    }

    class DashboardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var bookName: TextView = view.findViewById(R.id.txtBookName)
        var author: TextView = view.findViewById(R.id.txtBookAuthor)
        var price: TextView = view.findViewById(R.id.txtBookPrice)
        var rating: TextView = view.findViewById(R.id.txtBookRating)
        var bookImage: ImageView = view.findViewById(R.id.imgBookImage)
        var parentLinearlayout:androidx.cardview.widget.CardView = view.findViewById(R.id.parentLinearLayout)
    }
}