package com.internshala.bookworm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.internshala.bookworm.R

class DashboardViewAdapter(private val dataList:ArrayList<String>, val context:Context):RecyclerView.Adapter<DashboardViewAdapter.DashboardViewHolder> (){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.singlerow_of_recycler_view_dashboard,parent,false)
        return DashboardViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val text = dataList[position]
        holder.bookName.text = text
    }
    class DashboardViewHolder(view:View): RecyclerView.ViewHolder(view) {
                var bookName:TextView = view.findViewById(R.id.txtOfSingleItemOfRecyclerView)
    }


}