package com.internshala.bookworm.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.internshala.bookworm.Database.BookEntity
import com.internshala.bookworm.R
import com.internshala.bookworm.activities.Description
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.singlerow_of_recycler_view_dashboard.view.*

class FavouriteViewAdapter(val bookList:List<BookEntity>,val context:Context) :RecyclerView.Adapter<FavouriteViewAdapter.FavouriteViewHolder>(){
    class FavouriteViewHolder(view:View):RecyclerView.ViewHolder(view){
        var nameOfBook:TextView = view.findViewById(R.id.txtFavouriteBookName)
        var nameOfAuthor:TextView = view.findViewById(R.id.txtFavouriteBookAuthor)
        var priceOfBook:TextView = view.findViewById(R.id.txtFavouriteBookPrice)
        var ratingOfBook:TextView = view.findViewById(R.id.txtFavouriteBookRating)
        var picture:ImageView = view.findViewById(R.id.imgFaouriteBopokImage)
        var parentGridLayout:androidx.cardview.widget.CardView = view.findViewById(R.id.parentGridlayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_favorite_single_row, parent, false)
        return FavouriteViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        holder.nameOfBook.text = bookList[position].bookName
        holder.nameOfAuthor.text = bookList[position].bookAthuor
        holder.priceOfBook.text = bookList[position].bookPrice
        holder.ratingOfBook.text = bookList[position].bookRating
        Picasso.get().load(bookList[position].bookImage).error(R.drawable.default_book_cover).into(holder.picture)
        holder.parentGridLayout.setOnClickListener {
            val goToDescription = Intent(context, Description::class.java)
            goToDescription.putExtra("bookId",bookList[position].book_id.toString())
            context.startActivity(goToDescription)
        }
    }
}