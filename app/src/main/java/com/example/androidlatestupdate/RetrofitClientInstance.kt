package com.example.androidlatestupdate

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientInstance {

    lateinit var retrofit: Retrofit
    val retrofitInstance: Retrofit
        get() {
            if (!this::retrofit.isInitialized) {
                val okHttpClient = OkHttpClient()
                    .newBuilder()
                    .followRedirects(true)
                    .build()
                retrofit= Retrofit.Builder()
                    .baseUrl("https://fakestoreapi.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build()
            }
            return retrofit
        }
}