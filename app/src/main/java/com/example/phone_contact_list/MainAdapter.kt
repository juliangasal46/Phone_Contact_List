package com.example.phone_contact_list

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

class MainAdapter: RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    // Initialize other variables
    lateinit var activity: Activity
    lateinit var arrayList: ArrayList<ContactAdapter>

    // public constructor


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MainAdapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Initialize variables
        lateinit var tv_Name: TextView
        lateinit var tv_Number: TextView

        init {
            tv_Name = itemView.findViewById(R.id.tvName)
            tv_Number = itemView.findViewById(R.id.tvNumber)
        }
    }
}