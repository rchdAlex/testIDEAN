package com.example.testtechniqueidean

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testtechniqueidean.api.ApiData

class ghibliFilmAdapter(var films : List<ApiData>, val itemClickListener: View.OnClickListener ):
RecyclerView.Adapter<ghibliFilmAdapter.ViewHolder>(){



    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val title_View = itemView.findViewById<TextView>(R.id.title_film)
        val released_date_View = itemView.findViewById<TextView>(R.id.released_date)
        val image_film_View = itemView.findViewById<ImageView>(R.id.image_film)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.item_list_film,parent,false)
        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val film = films[position]
        holder.apply {
            title_View.text = film.title
            released_date_View.text= film.release_date.toString()
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