package com.blanco.semifinalapi.network

import com.blanco.semifinalapi.models.TweetModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface APIService {
    @GET("/tweet/blanco")
    fun getTweetList(): Call<List<TweetModel>>

    @GET("/tweet/blanco/{id}")
    fun getTweetById(@Path("id") id: String): Call<TweetModel>

    @DELETE("/tweet/blanco/{id}")
    fun deleteTweetById(@Path("id") id: String): Call<TweetModel>

    @POST("/tweet/blanco")
    fun createPost(@Body post: TweetModel): Call<TweetModel>

    @PUT("/tweet/blanco/{id}")
    fun updatePost(@Body post: TweetModel,@Path("id") id: String): Call<TweetModel>
}