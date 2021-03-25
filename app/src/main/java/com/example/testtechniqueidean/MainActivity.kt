package com.example.testtechniqueidean

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testtechniqueidean.api.ApiData
import com.example.testtechniqueidean.api.ApiRequest
import com.example.testtechniqueidean.api.Base_URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var adapter: ghibliFilmAdapter
    lateinit var films: List<ApiData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        films = emptyList<ApiData>()

        adapter = ghibliFilmAdapter(films, this)

        val recyclerView = findViewById<RecyclerView>(R.id.list_film)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val api = Retrofit.Builder()
                .baseUrl(Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiRequest::class.java)

        api.getFilmGhibli().enqueue(object : Callback<List<ApiData>> {
            override fun onResponse(call: Call<List<ApiData>>, response: Response<List<ApiData>>) {

                films = response.body()!!
                adapter.setData(films)
            }

            override fun onFailure(call: Call<List<ApiData>>, t: Throwable) {
                Log.d("MAIN", "onFailure: fail retrofit call")
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_film_detail,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.action_add_fav -> {return true}
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onClick(view: View?) {
        if (view?.tag != null) {
            showFilmDetail(view.tag as Int)
        }
    }

    fun showFilmDetail(filmIndex: Int) {

        val film = films[filmIndex]

        val intent = Intent(this, DetailFilmActivity::class.java)
        intent.putExtra(DetailFilmActivity.EXTRA_FILM, film)
        intent.putExtra(DetailFilmActivity.EXTRA_FILM_INDEX, filmIndex)
        startActivity(intent)

    }
}

