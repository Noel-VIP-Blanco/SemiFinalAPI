package com.blanco.semifinalapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.blanco.semifinalapi.databinding.ActivityAddTweetBinding
import com.blanco.semifinalapi.models.TweetModel
import com.blanco.semifinalapi.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddTweetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTweetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTweetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.save.setOnClickListener {
            if(!binding.tweetName.text.isNullOrBlank()||!binding.description.text.isNullOrBlank()) {
                createTweet()
            }
            else{
                showEmptyText()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun createTweet() {
        val post = TweetModel(
            id = "",
            name = binding.tweetName.text.toString(),
            description = binding.description.text.toString()
        )
        RetrofitClient.apiService.createPost(post).enqueue(object: Callback<TweetModel> {
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
    private fun showEmptyText(){
        Toast.makeText(this, "Enter data to the textbox.", Toast.LENGTH_SHORT).show()
    }
    private fun showSuccess() {
        Toast.makeText(this, "Successful to save data.", Toast.LENGTH_SHORT).show()
    }
    private fun showError() {
        Toast.makeText(this, "Failed to save data.", Toast.LENGTH_SHORT).show()
    }
}