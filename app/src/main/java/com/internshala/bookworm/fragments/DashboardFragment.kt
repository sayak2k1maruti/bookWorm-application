package com.internshala.bookworm.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.internshala.bookworm.adapter.DashboardViewAdapter
import com.internshala.bookworm.R
import com.internshala.bookworm.model.Book
import com.internshala.bookworm.util.ConnectionManager
import org.json.JSONException
import org.json.JSONObject

class DashboardFragment : Fragment() {
    lateinit var recyclerViewDashboard: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var dashboardViewAdapter: DashboardViewAdapter
    val dataList: ArrayList<Book> = arrayListOf()
    lateinit var progress_circular: ProgressBar
    lateinit var progressBar: RelativeLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dashBoardView = inflater.inflate(R.layout.fragment_dashboard, container, false)
        recyclerViewDashboard = dashBoardView.findViewById(R.id.recyclerViewDashboard)
        progress_circular = dashBoardView.findViewById(R.id.progress_circular)
        progressBar = dashBoardView.findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        try {
            progressBar.visibility = View.GONE
            if (ConnectionManager().checkConnectivity(activity as Context)) {
                val queue = Volley.newRequestQueue(activity as Context)
                val url = "http://13.235.250.119/v1/book/fetch_books/"

                val jsonObjectRequest =
                    object : JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {
                        val success = it.getBoolean("success")
                        if (success) {
                            val data = it.getJSONArray("data")
                            for (i in 0 until data.length()) {
                                val bookDetails = data.getJSONObject(i)
                                val book = Book(
                                    bookDetails.getString("book_id"),
                                    bookDetails.getString("name"),
                                    bookDetails.getString("price"),
                                    bookDetails.getString("author"),
                                    bookDetails.getString("rating"),
                                    bookDetails.getString("image")
                                )
                                dataList.add(book)
                            }
                            layoutManager = LinearLayoutManager(activity)
                            dashboardViewAdapter =
                                DashboardViewAdapter(
                                    dataList,
                                    activity as Context
                                )   /*adapter object is created*/
                            recyclerViewDashboard.adapter = dashboardViewAdapter
                            recyclerViewDashboard.layoutManager = layoutManager
                        }

                    }, Response.ErrorListener {
                        Toast.makeText(activity, "!!Some error Occurs!!", Toast.LENGTH_LONG).show()
                    }) {

                        override fun getHeaders(): MutableMap<String, String> {
                            val headers = HashMap<String, String>()
                            headers["Content-type"] = "application/json"
                            headers["token"] = "f483c3c822da32"
                            return headers
                        }
                    }
                queue.add(jsonObjectRequest)

            } else {
                val dialog = AlertDialog.Builder(activity as Context)
                dialog.setTitle("Fail to Connect")
                dialog.setMessage("!!No Internet Connection!!")
                dialog.setPositiveButton("Open Setting") { text, listener ->
                    val settingIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                    startActivity(settingIntent)
                    activity?.finish()
                }
                dialog.setNegativeButton("Exit") { text, listener ->
                    ActivityCompat.finishAffinity(activity as Activity)
                }
                dialog.create()
                dialog.show()
            }
        } catch (e: JSONException) {
            Toast.makeText(activity as Context, "Some eror occurs", Toast.LENGTH_SHORT).show()
        }

        return dashBoardView
    }
}