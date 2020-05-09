package com.internshala.bookworm.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.internshala.bookworm.Database.BookDatabase
import com.internshala.bookworm.Database.BookEntity
import com.internshala.bookworm.R
import com.internshala.bookworm.util.ConnectionManager
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject

lateinit var tool_bar: androidx.appcompat.widget.Toolbar
lateinit var imgBook: ImageView
lateinit var txtbookname: TextView
lateinit var txtbookAuthor: TextView
lateinit var txtbookPrice: TextView
lateinit var txtbookRating: TextView
lateinit var txtdescriptionOfBook: TextView
lateinit var btnAddtoFavourite: Button
lateinit var rlProgressLayout: RelativeLayout
lateinit var progressbar: ProgressBar

class Description : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        tool_bar = findViewById(R.id.tool_bar)
        imgBook = findViewById(R.id.imgBookImage)
        txtbookname = findViewById(R.id.txtBookName)
        txtbookAuthor = findViewById(R.id.txtBookAuthor)
        txtbookPrice = findViewById(R.id.txtBookPrice)
        txtbookRating = findViewById(R.id.txtBookRating)
        txtdescriptionOfBook = findViewById(R.id.txtDescriptionOfBook)
        btnAddtoFavourite = findViewById(R.id.btnAddToFavourites)
        rlProgressLayout = findViewById(R.id.progressLayout)
        progressbar = findViewById(R.id.progressBar)
        rlProgressLayout.visibility = View.VISIBLE
        progressbar.visibility = View.VISIBLE
        setSupportActionBar(tool_bar)
        supportActionBar?.title = "Description"
        var bookId: String? = "100"

        if (intent != null) {
            bookId = intent.getStringExtra("bookId")
            val jsonPut = JSONObject()
            jsonPut.put("book_id", bookId)
            val url = "http://13.235.250.119/v1/book/get_book/"
            val queue = Volley.newRequestQueue(this@Description)
            if (ConnectionManager().checkConnectivity(this@Description)) {
                try {
                    val jsonObjectRequest =
                        object :
                            JsonObjectRequest(Request.Method.POST, url, jsonPut, Response.Listener {

                                val success = it.getBoolean("success")
                                if (success) {

                                    rlProgressLayout.visibility = View.GONE
                                    progressbar.visibility = View.GONE
                                    val data: JSONObject = it.getJSONObject("book_data")
                                    val image = data.getString("image")
                                    Picasso.get().load(data.getString("image"))
                                        .error(R.drawable.default_book_cover).into(
                                            imgBook
                                        )
                                    txtbookname.text = data.getString("name")
                                    supportActionBar?.title = data.getString("name")
                                    txtbookAuthor.text = data.getString("author")
                                    txtbookPrice.text = data.getString("price")
                                    txtbookRating.text = data.getString("rating")
                                    txtdescriptionOfBook.text = data.getString("description")

                                    val bookEntity = BookEntity(
                                        bookId.toInt(),
                                        txtbookname.text.toString(),
                                        txtbookPrice.text.toString(),
                                        txtbookAuthor.text.toString(),
                                        txtbookRating.text.toString(),
                                        image
                                    )
                                    val checkFav = DBAsyncTask(applicationContext,bookEntity,1).execute()
                                    val isFavourite:Boolean = checkFav.get()
                                    if (isFavourite){
                                        btnAddtoFavourite.text = "Remove From Favourite"
                                        val color = ContextCompat.getColor(applicationContext,R.color.colorAccent)
                                        btnAddtoFavourite.setBackgroundColor(color)
                                    }else{
                                        btnAddtoFavourite.text = "Add to Favourite"
                                        val color = ContextCompat.getColor(applicationContext,R.color.colorPrimary)
                                        btnAddtoFavourite.setBackgroundColor(color)
                                    }

                                    btnAddtoFavourite.setOnClickListener {
                                        if(!(DBAsyncTask(applicationContext,bookEntity,1).execute().get())){
                                            DBAsyncTask(applicationContext,bookEntity,2).execute().get()
                                            Toast.makeText(applicationContext,"Successfully added to Favourite",Toast.LENGTH_SHORT).show()
                                            btnAddtoFavourite.text = "Remove From Favourite"
                                            val color = ContextCompat.getColor(applicationContext,R.color.colorAccent)
                                            btnAddtoFavourite.setBackgroundColor(color)
                                        }else{
                                            DBAsyncTask(applicationContext,bookEntity,3).execute().get()
                                            Toast.makeText(applicationContext,"Successfully removed Favourite",Toast.LENGTH_SHORT).show()
                                            btnAddtoFavourite.text = "Add to Favourite"
                                            val color = ContextCompat.getColor(applicationContext,R.color.colorPrimary)
                                            btnAddtoFavourite.setBackgroundColor(color)
                                        }
                                    }
                                } else {
                                    Toast.makeText(
                                        this@Description,
                                        "!!Some unexpected error occurs!!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }, Response.ErrorListener {
                                Toast.makeText(
                                    this@Description,
                                    "!!Some unexpected error Occurs!!",
                                    Toast.LENGTH_LONG
                                ).show()
                            }) {

                            override fun getHeaders(): MutableMap<String, String> {
                                val headers = HashMap<String, String>()
                                headers["Content-type"] = "application/json"
                                headers["token"] = "f483c3c822da32"
                                return headers
                            }
                        }
                    queue.add(jsonObjectRequest)
                } catch (e: JSONException) {
                    Toast.makeText(
                        this@Description,
                        "!!Some unexpected error occurs!!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                val dialog = AlertDialog.Builder(this@Description)
                dialog.setTitle("Fail to Connect")
                dialog.setMessage("!!No Internet Connection!!")
                dialog.setPositiveButton("Open Setting") { text, listener ->
                    val settingIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                    startActivity(settingIntent)
                    this@Description.finish()
                }
                dialog.setNegativeButton("Exit") { text, listener ->
                    ActivityCompat.finishAffinity(this@Description)
                }
                dialog.create()
                dialog.show()
            }

        } else {
            Toast.makeText(this@Description, "Some Unexpected Error occurs", Toast.LENGTH_SHORT)
                .show()
        }
    }
}

class DBAsyncTask(val context: Context, val bookEntity: BookEntity, val mode: Int) :
    AsyncTask<Void, Void, Boolean>() {

    val db = Room.databaseBuilder(context, BookDatabase::class.java, "book-db").build()
    override fun doInBackground(vararg params: Void?): Boolean {
        when (mode) {
            1 -> {
                /*check if it is in favourite or not*/
                val book: BookEntity? = db.bookdao().getBookById(bookEntity.book_id.toString())
                db.close()
                return (book != null) /*if book is in databse then book!=null so it will return true otherwise true*/
            }
            2 -> {
                /*Insert a new entities*/
                db.bookdao().insertNew(bookEntity)
                db.close()
                return true
            }
            3 -> {
                /*Delete entities*/
                db.bookdao().deleteBook(bookEntity)
                db.close()
                return true
            }
        }
        return false
    }

}