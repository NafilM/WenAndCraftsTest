package com.example.testapp.api

import com.example.testapp.model.CategoryResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface ApiInterface {

    @POST("v2/5ec39cba300000720039c1f6")
    fun categoryFetch(): Call<CategoryResponse>

    companion object {

        var apiInterface: ApiInterface? = null
        val BASE_URL: String = "http://www.mocky.io/"

        //Create the Retrofit service instance using the retrofit.
        fun getInstance(): ApiInterface {

            if (apiInterface == null) {

                val okHttpClient = OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS) // Set the connection timeout to 30 seconds
                    .readTimeout(30, TimeUnit.SECONDS)    // Set the read timeout to 30 seconds
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()
                apiInterface = retrofit.create(ApiInterface::class.java)
            }
            return apiInterface!!
        }
    }
}