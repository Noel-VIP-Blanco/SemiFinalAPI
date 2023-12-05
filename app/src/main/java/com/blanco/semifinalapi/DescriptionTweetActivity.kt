package com.blanco.semifinalapi

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.blanco.semifinalapi.constants.Constants
import com.blanco.semifinalapi.databinding.ActivityDescriptionTweetBinding
import com.blanco.semifinalapi.models.TweetModel
import com.blanco.semifinalapi.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DescriptionTweetActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDescriptionTweetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDescriptionTweetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val tweetId = intent.getStringExtra(Constants.PARAM_ID)
        val tweetName = intent.getStringExtra(Constants.PARAM_TWEET_NAME)
        val tweetDescription = intent.getStringExtra(Constants.PARAM_TWEET_DESCRIPTION)
        binding.tweetName.text =  tweetName
        binding.tweetDescription.text =  tweetDescription
        binding.deleteTheTweetButton.setOnClickListener {
            if(tweetId != null){
                deleteTweet(tweetId)
                finish()
            }
        }
        binding.updateTheTweetButton.setOnClickListener {
            val intent = Intent(this, UpdateTweetActivity::class.java)
            intent.putExtra(Constants.PARAM_ID, tweetId)
            intent.putExtra(Constants.PARAM_TWEET_NAME, tweetName)
            intent.putExtra(Constants.PARAM_TWEET_DESCRIPTION, tweetDescription)
            this.startActivity(intent)
        }
    }

    private fun deleteTweet(tweetId:String){
        RetrofitClient.apiService.deleteTweetById(tweetId).enqueue(object: Callback<TweetModel> {
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
        Toast.makeText(this, "Delete Successful.", Toast.LENGTH_SHORT).show()
    }
    private fun showError() {
        Toast.makeText(this, "Delete Failed.", Toast.LENGTH_SHORT).show()
    }

}