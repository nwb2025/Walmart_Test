package com.example.countries.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.countries.R
import com.example.countries.data.Country


class MainRecyclerAdapter(val context: Context,
                          val gifs: List<Country>):
    RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder>()

{
    override fun getItemCount() = gifs.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.country_grid_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val gif = gifs[position]
        with(holder) {
            name?.let {
                it.text = gif.capital
                it.contentDescription = gif.region
            }
            region?.let {
                it.text = gif.region
            }

            code?.let {
                it.text = gif.code
            }
            capital?.let {
                it.text = gif.capital
            }
           /*Glide.with(context)
                .load(gif.flag.toString())
               .error(R.drawable.default_img)
                .into(gifImage)*/
        }
    }

    inner class ViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.name)
        val region = itemView.findViewById<TextView>(R.id.region)
        val code = itemView.findViewById<TextView>(R.id.code)
        val capital = itemView.findViewById<TextView>(R.id.capital)

    }

}