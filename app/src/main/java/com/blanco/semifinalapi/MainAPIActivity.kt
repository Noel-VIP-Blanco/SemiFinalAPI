package com.blanco.semifinalapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.blanco.semifinalapi.adapters.TweetAdapter
import com.blanco.semifinalapi.databinding.ActivityMainApiactivityBinding
import com.blanco.semifinalapi.models.TweetModel
import com.blanco.semifinalapi.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainAPIActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainApiactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainApiactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.add.setOnClickListener {
            startActivity(Intent(this, AddTweetActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        getTweet()
    }

    private fun getTweet() {
        val activity = this
        RetrofitClient.apiService.getTweetList().enqueue(object: Callback<List<TweetModel>> {
            override fun onResponse(call: Call<List<TweetModel>>, response: Response<List<TweetModel>>) {
                if (response.isSuccessful) {
                    val data: List<TweetModel>? = response.body()
                    if(data != null) {
                        binding.tweetList.layoutManager = LinearLayoutManager(activity)
                        binding.tweetList.adapter = TweetAdapter(activity, data)
                        binding.progress.visibility = View.GONE
                    }
                } else {
                    showError()
                }
            }

            override fun onFailure(call: Call<List<TweetModel>>, t: Throwable) {
                showError()
            }
        })
    }

    private fun showError() {
        Toast.makeText(this, "Failed to load data.", Toast.LENGTH_SHORT).show()
    }
}