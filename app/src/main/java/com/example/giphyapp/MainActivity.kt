package com.example.giphyapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.giphyapp.adapter.GifsAdapter
import com.example.giphyapp.data.DataObject
import com.example.giphyapp.data.DataService
import com.example.giphyapp.data.GifsListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.giphy.com/v1/"
const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val gifs = mutableListOf<DataObject>()
        val adapter = GifsAdapter(this, gifs)

        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        //settings up onItemClickListener
        adapter.setOnItemClickListener(object : GifsAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                val intent = Intent(this@MainActivity, SecondActivity::class.java)

                intent.putExtra("url", gifs[position].images.ogImage.url)
                startActivity(intent)
            }

        })

        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //service
        val retroService = retrofit.create(DataService::class.java)
        retroService.getGifs().enqueue(object : Callback<GifsListResponse?> {
            override fun onResponse(
                call: Call<GifsListResponse?>,
                response: Response<GifsListResponse?>
            ) {
                val body = response.body()
                if (body == null) {
                    Log.d(TAG, "onResponse: No response... ")
                }
                    //add to list
                gifs.addAll(body!!.data)
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<GifsListResponse?>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}