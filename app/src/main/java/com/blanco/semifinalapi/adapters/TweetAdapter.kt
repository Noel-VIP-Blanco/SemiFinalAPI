package com.blanco.semifinalapi.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blanco.semifinalapi.DescriptionTweetActivity
import com.blanco.semifinalapi.constants.Constants
import com.blanco.semifinalapi.databinding.TweetItemBinding
import com.blanco.semifinalapi.models.TweetModel

class TweetAdapter(
    private val activity: Activity,
    private val tweetList: List<TweetModel>,
    ):RecyclerView.Adapter<TweetAdapter.TweetViewHolder>() {

        class TweetViewHolder(
            private val activity: Activity,
            private val binding: TweetItemBinding,
        ): RecyclerView.ViewHolder(binding.root){
            fun bind(tweet: TweetModel) {
                binding.tweetName.text = tweet.name
                binding.tweetDescription.text = tweet.description
                binding.root.setOnClickListener {
                    val intent = Intent(activity, DescriptionTweetActivity::class.java)
                    intent.putExtra(Constants.PARAM_ID, tweet.id)
                    intent.putExtra(Constants.PARAM_TWEET_NAME, tweet.name)
                    intent.putExtra(Constants.PARAM_TWEET_DESCRIPTION, tweet.description)
                    activity.startActivity(intent)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TweetItemBinding.inflate(
            inflater,
            parent,
            false,
        )
        return TweetViewHolder(activity, binding)
    }

    override fun getItemCount() = tweetList.size

    override fun onBindViewHolder(holder: TweetViewHolder, position: Int) {
        holder.bind(tweetList[position])
    }
}