package com.internshala.bookworm.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.internshala.bookworm.adapter.DashboardViewAdapter
import com.internshala.bookworm.R

class DashboardFragment : Fragment() {
    lateinit var recyclerViewDashboard:RecyclerView
    lateinit var layoutManager:RecyclerView.LayoutManager
    lateinit var dashboardViewAdapter: DashboardViewAdapter
    val dataList:ArrayList<String> = arrayListOf(
                "Tintin in the Land of the Soviets - (Tintin au pays des Soviets) (1929–1930)" ,
                "Tintin in the Congo - (Tintin au Congo) (1930–1931)" ,
                "Tintin in America - (Tintin en Amérique) (1931–1932)" ,
                "Cigars of the Pharaoh - (Les Cigares du Pharaon) (1932–1934) " ,
                "The Blue Lotus - (Le Lotus bleu) (1934–1935) " ,
                "The Broken Ear - (L'Oreille cassée) (1935–1937) " ,
                "The Black Island - (L'Ile noire) (1937–1938) " ,
                "King Ottokar's Sceptre - (Le Sceptre d'Ottokar) (1938–1939) " ,
                "The Crab with the Golden Claws - (Le Crabe aux pinces d'or) (1940–1941) " ,
                "The Shooting Star - (L'Etoile mystérieuse) (1941–1942) " ,
                "The Secret of the Unicorn - (Le Secret de la Licorne) (1942–1943) " ,
                "Red Rackham's Treasure - (Le Trésor de Rackam le Rouge) (1943) " ,
                "The Seve                       n Crystal Balls - (Les Sept boules de cristal) (1943–1946) " ,
                "Prisoners of the Sun - (Le Temple du soleil) (1946–1948) " ,
                "Land of Black Gold - (Tintin au pays de l'or noir) (1948–1950) 1 " ,
                "Destination Moon - (Objectif Lune) (1950–1953) " ,
                "Explorers on the Moon - (On a marché sur la Lune) (1950–1953) " ,
                "The Calculus Affair - (L'Affaire Tournesol) (1954–1956) " ,
                "The Red Sea Sharks - (Coke en stock) (1956–1958) " ,
                "Tintin in Tibet - (Tintin au Tibet) (1958–1959) " ,
                "The Castafiore Emerald - (Les Bijoux de la Castafiore) (1961–1962) " ,
                "Flight 714 to Sydney - (Vol 714 pour Sydney) (1966–1967) " ,
                "Tintin and the Picaros - (Tintin et les Picaros) (1975–1976) " ,
                "Tintin and Alph-Art - (Tintin et l'Alph-Art): Unfinished work, published posthumously in 1986, and republished with more material in 2004"
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
        return dashBoardView
    }

}