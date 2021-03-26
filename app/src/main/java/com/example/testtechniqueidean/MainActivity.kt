package com.example.testtechniqueidean

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ToggleButton
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testtechniqueidean.api.ApiData
import com.example.testtechniqueidean.api.ApiRequest
import com.example.testtechniqueidean.api.Base_URL
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var adapter: ghibliFilmAdapter
    lateinit var films: MutableList<ApiData>
    lateinit var recyclerView: RecyclerView
    lateinit var imageFilms: MutableList<String>
    lateinit var check: ToggleButton
    lateinit var retry_call_button:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        retry_call_button = findViewById<Button>(R.id.retry_call_api)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)


        films = mutableListOf()
        adapter = ghibliFilmAdapter(films, this)

        recyclerView = findViewById<RecyclerView>(R.id.list_film)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        makeRequest()
        retry_call_button.isVisible = false
    }

    private fun makeRequest() {
        val api = Retrofit.Builder()
                .baseUrl(Base_URL)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiRequest::class.java)

        /*  api.getFilmGhibli().enqueue(object : Callback<List<ApiData>> {
              override fun onResponse(call: Call<List<ApiData>>, response: Response<List<ApiData>>) {

                  films = (response.body() as MutableList<ApiData>?)!!
                  adapter.setData(films)
              }

              override fun onFailure(call: Call<List<ApiData>>, t: Throwable) {
                  Log.d("MAIN", "onFailure: fail retrofit call")
              }

          })*/ // cela fonctionne

        val compositeDisposable = CompositeDisposable()

        compositeDisposable.add(
                api.getFilmGhibli()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ response ->
                            onResponse(response)
                        },
                                { t ->
                                    onFailure(t)
                                    retry_call_button.isVisible = true
                                    retry_call_button.setOnClickListener { onClickRetryCall() }

                                })
        )
    }


    private fun onFailure(t: Throwable?) {
        Log.d("Main", "onFailure: $t")
    }

    private fun onResponse(response: List<ApiData>) {
        films = response as MutableList<ApiData>
        adapter.setData(films)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main_film_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.display_liked_film -> {
                displayLikedFilm()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun displayLikedFilm() {
        films.filter {
            it.liked
        }
        adapter.notifyDataSetChanged()
    }

    override fun onClick(view: View?) {
        if (view?.tag != null) {
            showFilmDetail(view.tag as Int)
        }
    }

    private fun showFilmDetail(filmIndex: Int) {

        val film = films[filmIndex]

        val intent = Intent(this, DetailFilmActivity::class.java)
        intent.putExtra(DetailFilmActivity.EXTRA_FILM, film)
        intent.putExtra(DetailFilmActivity.EXTRA_FILM_INDEX, filmIndex)
        startActivityForResult(intent, DetailFilmActivity.REQUEST_EDIT_FILM)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK || data == null) {
            return
        }

        when (requestCode) {
            DetailFilmActivity.REQUEST_EDIT_FILM -> processEditFilmResult(data)
        }
    }

    private fun processEditFilmResult(data: Intent) {
        val filmIndex = data.getIntExtra(DetailFilmActivity.EXTRA_FILM_INDEX, -1)

        val film = data.getParcelableExtra<ApiData>(DetailFilmActivity.EXTRA_FILM)
        if (film != null) {
            saveFilm(film, filmIndex)
        }
    }

    private fun saveFilm(film: ApiData, filmIndex: Int) {

        films[filmIndex].liked = film.liked

        adapter.notifyDataSetChanged()

    }

    fun onClickRetryCall() {
        makeRequest()
        retry_call_button.isVisible = false
    }


}

