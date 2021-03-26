package com.example.testtechniqueidean

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testtechniqueidean.api.ApiData
import java.io.File


class ghibliFilmAdapter(var films : List<ApiData>, val itemClickListener: View.OnClickListener ):
RecyclerView.Adapter<ghibliFilmAdapter.ViewHolder>(){


    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val cardView = itemView.findViewById<CardView>(R.id.card_view)
        val title_View = itemView.findViewById<TextView>(R.id.title_film)
        val released_date_View = itemView.findViewById<TextView>(R.id.released_date)
        val image_film_View = itemView.findViewById<ImageView>(R.id.image_film)
        val like_button_View = itemView.findViewById<ToggleButton>(R.id.liked_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.item_list_film,parent,false)
        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val film = films[position]
        holder.apply {
            cardView.setOnClickListener(itemClickListener)
            cardView.tag = position
            title_View.text = film.title
            released_date_View.text= film.release_date.toString()
            image_film_View.setImageResource(R.drawable.porco_rosse)
            like_button_View.setOnClickListener {
                if (like_button_View.isChecked){
                    films[position].liked =true
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return films.size
    }


    fun setData(newdata: List<ApiData>) {
        this.films = newdata
        films.sortedBy { it.release_date } // ordering film by release date
        notifyDataSetChanged()
    }

}