package com.example.testtechniqueidean

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.example.testtechniqueidean.api.ApiData

class DetailFilmActivity : AppCompatActivity() {

    companion object {
        val EXTRA_FILM = "film"
        val EXTRA_FILM_INDEX = "filmIndex"
    }

    lateinit var film: ApiData
    var filmIndex: Int = -1

    lateinit var titleFilm: TextView
    lateinit var dateFilm: TextView
    lateinit var producteurFilm: TextView
    lateinit var directeurFilm: TextView
    lateinit var descriptionFilm: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_film)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        film = intent.getParcelableExtra<ApiData>(EXTRA_FILM)!!
        filmIndex = intent.getIntExtra(EXTRA_FILM_INDEX, -1)

        titleFilm = findViewById(R.id.title_film_detail)
        dateFilm = findViewById(R.id.released_date_detail)
        producteurFilm = findViewById(R.id.produtor_detail)
        directeurFilm = findViewById(R.id.director_detail)
        descriptionFilm = findViewById(R.id.description_film_detail)

        titleFilm.text = film.title
        dateFilm.text = film.release_date.toString()
        producteurFilm.text = film.producer
        directeurFilm.text = film.director
        descriptionFilm.text = film.description
    }

    fun onClickAddFavoriteFilm(view: View) {
        view.setOnClickListener {
            film.liked =true
        }
    }


}