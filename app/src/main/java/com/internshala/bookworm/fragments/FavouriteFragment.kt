package com.internshala.bookworm.fragments

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
import com.internshala.bookworm.Database.BookDatabase
import com.internshala.bookworm.Database.BookEntity
import com.internshala.bookworm.R
import com.internshala.bookworm.adapter.FavouriteViewAdapter

/**
 * A simple [Fragment] subclass.
 */
class FavouriteFragment : Fragment() {
    lateinit var favouriterecyclerView: androidx.recyclerview.widget.RecyclerView
    lateinit var favouriteProgressLayout: RelativeLayout
    lateinit var favouriteProgressBar: ProgressBar
    var bookListOfFavourite: List<BookEntity> = listOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val favouriteInflater = inflater.inflate(R.layout.fragment_favourite, container, false)
        favouriterecyclerView = favouriteInflater.findViewById(R.id.recyclerViewFavourite)
        favouriteProgressLayout = favouriteInflater.findViewById(R.id.favouriteProgressLayOut)
        favouriteProgressBar = favouriteInflater.findViewById(R.id.favouriteProgressBar)
        favouriteProgressLayout.visibility = View.VISIBLE
        favouriteProgressBar.visibility = View.VISIBLE
        bookListOfFavourite = retriveFavorite(activity as Context).execute().get()
        val layoutManager = GridLayoutManager(activity, 2)
        if (activity != null) {
            val favouriteViewAdapter =
                FavouriteViewAdapter(bookListOfFavourite, activity as Context)
            favouriterecyclerView.layoutManager = layoutManager
            favouriterecyclerView.adapter = favouriteViewAdapter
            favouriteProgressBar.visibility = View.GONE
            favouriteProgressLayout.visibility = View.GONE
        }
        return favouriteInflater
    }

    class retriveFavorite(val context: Context) : AsyncTask<Void, Void, List<BookEntity>>() {
        override fun doInBackground(vararg params: Void?): List<BookEntity> {
            val db = Room.databaseBuilder(context, BookDatabase::class.java, "book-db").build()
            return db.bookdao().getAllBooks()
        }

    }
}
