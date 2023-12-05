package com.blanco.semifinalapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.blanco.semifinalapi.constants.Constants
import com.blanco.semifinalapi.databinding.ActivityUpdateTweetBinding
import com.blanco.semifinalapi.models.TweetModel
import com.blanco.semifinalapi.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateTweetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateTweetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateTweetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.update.setOnClickListener {

            if(!binding.tweetName.text.isNullOrBlank()||!binding.description.text.isNullOrBlank()) {
                updateTweet()
            }
        }
    }

    private fun updateTweet() {
        val tweetId = intent.getStringExtra(Constants.PARAM_ID)

        val post = TweetModel(
            id = tweetId.toString(),
            name = binding.tweetName.text.toString(),
            description = binding.description.text.toString()
        )
        RetrofitClient.apiService.updatePost(post, tweetId.toString()).enqueue(object: Callback<TweetModel> {
            override fun onResponse(call: Call<TweetModel>, response: Response<TweetModel>) {
                if (response.isSuccessful) {
                    showSuccess()

                    finish()
                } else {
                    showError()
                }
            }

            override fun onFailure(call: Call<TweetModel>, t: Throwable) {
                showError()
            }
        })
    }
    private fun showSuccess() {
        Toast.makeText(this, "Update Successful.", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainAPIActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
    private fun showError() {
        Toast.makeText(this, "Update Failed.", Toast.LENGTH_SHORT).show()
    }
}