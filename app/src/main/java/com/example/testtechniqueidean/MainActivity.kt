package com.example.testtechniqueidean

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        adapter = ghibliFilmAdapter(emptyList<ApiData>(), this)

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

                adapter.setData(response.body()!!)
            }

            override fun onFailure(call: Call<List<ApiData>>, t: Throwable) {
                Log.d("MAIN", "onFailure: fail retrofit call")
            }

        })
    }

    override fun onClick(view: View?) {
        Toast.makeText(this, "bonjour", Toast.LENGTH_SHORT).show()
    }
}

