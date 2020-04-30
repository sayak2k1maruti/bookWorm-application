package com.internshala.bookworm.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.internshala.bookworm.adapter.DashboardViewAdapter
import com.internshala.bookworm.R
import com.internshala.bookworm.model.Book

class DashboardFragment : Fragment() {
    lateinit var recyclerViewDashboard:RecyclerView
    lateinit var layoutManager:RecyclerView.LayoutManager
    lateinit var dashboardViewAdapter: DashboardViewAdapter
    val dataList:ArrayList<Book> = arrayListOf(
        Book("Tintin in America(1931)","Rs. 359","by Hergé","4.7",R.drawable.tintin_in_america) ,
        Book("Cigars of the Pharaoh(1934)","Rs. 495","by Hergé","4.8",R.drawable.cigars_of_the_pharaoh) ,
        Book("The Broken Ear(1937)","Rs. 357","by Hergé","4.7",R.drawable.the_broken_ear) ,
        Book("The Crab with the Golden Claws(1941)","Rs. 359","by Hergé","4.7",R.drawable.the_crabwith_the_golden_claws) ,
        Book("The Secret of the Unicorn(1943)","Rs. 359","by Hergé","4.7",R.drawable.the_secret_of_the_unicorn) ,
        Book("Red Rackham's Treasure(1943)","Rs. 349","by Hergé","4.7",R.drawable.red_rackhams_treasure) ,
        Book("Land of Black Gold(1950)","Rs. 359","by Hergé","4.7",R.drawable.land_of_black_gold) ,
        Book("Explorers on the Moon(1952)","Rs. 599","by Hergé","4.7",R.drawable.explorers_on_the_moon) ,
        Book("Tintin in Tibet(1959)","Rs. 529","by Hergé","4.8",R.drawable.tintin_in_tibet) ,
        Book("The Castafiore Emerald(1962)","Rs. 599","by Hergé","4.7",R.drawable.the_castafiore_emerald) ,
        Book("Flight 714 to Sydney(1967)","Rs. 349","by Hergé","4.7",R.drawable.flight_seven_one_four_to_sydney) ,
        Book("Tintin and the Picaros(1976)","Rs. 599","by Hergé","4.8",R.drawable.tintin_and_the_picaros)
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dashBoardView = inflater.inflate(R.layout.fragment_dashboard, container, false)
        recyclerViewDashboard = dashBoardView.findViewById(R.id.recyclerViewDashboard)
        layoutManager = LinearLayoutManager(activity)
        dashboardViewAdapter =
            DashboardViewAdapter(
                dataList,
                activity as Context
            )   /*adapter object is created*/
        recyclerViewDashboard.adapter = dashboardViewAdapter
        recyclerViewDashboard.layoutManager =layoutManager
        recyclerViewDashboard.addItemDecoration(
            DividerItemDecoration(
                recyclerViewDashboard.context ,
                (layoutManager as LinearLayoutManager).orientation
            )
        )
        return dashBoardView
    }

}