package com.example.testtechniqueidean

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.example.testtechniqueidean.api.ApiData

private const val KEY_SAVE_LIKE = "index"

class DetailFilmActivity : AppCompatActivity() {

    companion object {
        val REQUEST_EDIT_FILM = 0
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
    lateinit var likeButton: ToggleButton
    lateinit var imgFilm: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_film)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        film = intent.getParcelableExtra<ApiData>(EXTRA_FILM)!!
        filmIndex = intent.getIntExtra(EXTRA_FILM_INDEX, -1)

        titleFilm = findViewById(R.id.title_film_detail)
        dateFilm = findViewById(R.id.released_date_detail)
        producteurFilm = findViewById(R.id.produtor_detail)
        directeurFilm = findViewById(R.id.director_detail)
        descriptionFilm = findViewById(R.id.description_film_detail)
        imgFilm = findViewById(R.id.image_film_detail)
        likeButton = findViewById(R.id.liked_button_detail)

        titleFilm.text = film.title
        dateFilm.text = film.release_date.toString()
        producteurFilm.text = film.producer
        directeurFilm.text = film.director
        descriptionFilm.text = film.description
        likeButton.isChecked = savedInstanceState?.getBoolean(KEY_SAVE_LIKE) ?: film.liked
        Glide.with(this).load(film.image).into(imgFilm)
    }

    // Function add movie to favorite movie
    fun onClickAddFavoriteFilm(view: View) {
        likeButton.setOnClickListener {
            if (!film.liked)
            film.liked = true
        }
    }
/***********************Menu***********************************/
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_film_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.back_home-> {
                saveFilm()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
/**********************************************************/


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_SAVE_LIKE, film.liked)
        Log.d("DETAIL", "onSaveInstanceState: called")
    }

    // function save changes detail
    private fun saveFilm() {
        film.liked = likeButton.isChecked
        intent = Intent()
        intent.putExtra(EXTRA_FILM, film)
        intent.putExtra(EXTRA_FILM_INDEX, filmIndex)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

}