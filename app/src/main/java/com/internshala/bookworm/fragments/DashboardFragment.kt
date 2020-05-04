package com.internshala.bookworm.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
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
import org.json.JSONObject

class DashboardFragment : Fragment() {
    lateinit var recyclerViewDashboard: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var dashboardViewAdapter: DashboardViewAdapter
    lateinit var btnCheckInternet: Button
    val dataList: ArrayList<Book> = arrayListOf()
    /*val dataList: ArrayList<Book> = arrayListOf(
        Book("Tintin in America(1931)", "Rs. 359", "by Hergé", "4.7", R.drawable.tintin_in_america),
        Book(
            "Cigars of the Pharaoh(1934)",
            "Rs. 495",
            "by Hergé",
            "4.8",
            R.drawable.cigars_of_the_pharaoh
        ),
        Book("The Broken Ear(1937)", "Rs. 357", "by Hergé", "4.7", R.drawable.the_broken_ear),
        Book(
            "The Crab with the Golden Claws(1941)",
            "Rs. 359",
            "by Hergé",
            "4.7",
            R.drawable.the_crabwith_the_golden_claws
        ),
        Book(
            "The Secret of the Unicorn(1943)",
            "Rs. 359",
            "by Hergé",
            "4.7",
            R.drawable.the_secret_of_the_unicorn
        ),
        Book(
            "Red Rackham's Treasure(1943)",
            "Rs. 349",
            "by Hergé",
            "4.7",
            R.drawable.red_rackhams_treasure
        ),
        Book(
            "Land of Black Gold(1950)",
            "Rs. 359",
            "by Hergé",
            "4.7",
            R.drawable.land_of_black_gold
        ),
        Book(
            "Explorers on the Moon(1952)",
            "Rs. 599",
            "by Hergé",
            "4.7",
            R.drawable.explorers_on_the_moon
        ),
        Book("Tintin in Tibet(1959)", "Rs. 529", "by Hergé", "4.8", R.drawable.tintin_in_tibet),
        Book(
            "The Castafiore Emerald(1962)",
            "Rs. 599",
            "by Hergé",
            "4.7",
            R.drawable.the_castafiore_emerald
        ),
        Book(
            "Flight 714 to Sydney(1967)",
            "Rs. 349",
            "by Hergé",
            "4.7",
            R.drawable.flight_seven_one_four_to_sydney
        ),
        Book(
            "Tintin and the Picaros(1976)",
            "Rs. 599",
            "by Hergé",
            "4.8",
            R.drawable.tintin_and_the_picaros
        )
    )*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dashBoardView = inflater.inflate(R.layout.fragment_dashboard, container, false)
        btnCheckInternet = dashBoardView.findViewById(R.id.btnCheckInternet)
        recyclerViewDashboard = dashBoardView.findViewById(R.id.recyclerViewDashboard)

        btnCheckInternet.setOnClickListener {
            if (ConnectionManager().checkConnectivity(activity as Context)) {
                val dialog = AlertDialog.Builder(activity as Context)
                dialog.setTitle("Success")
                dialog.setMessage("Internet Connection is Active")
                dialog.setPositiveButton("Ok") { text, listener -> }
                dialog.setNegativeButton("Cancel") { text, listener -> }
                dialog.create()
                dialog.show()
            } else {
                val dialog = AlertDialog.Builder(activity as Context)
                dialog.setTitle("Fail to Connect")
                dialog.setMessage("!!No Internet Connection!!")
                dialog.setPositiveButton("Ok") { text, listener -> }
                dialog.setNegativeButton("Cancel") { text, listener -> }
                dialog.create()
                dialog.show()
            }
        }
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
                    recyclerViewDashboard.addItemDecoration(
                        DividerItemDecoration(
                            recyclerViewDashboard.context,
                            (layoutManager as LinearLayoutManager).orientation
                        )
                    )
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
        return dashBoardView
    }
}