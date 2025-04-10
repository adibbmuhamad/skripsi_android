package com.example.projectskripsi.data.network

import com.example.projectskripsi.ui.component.CustomDateTypeAdapter
import com.example.projectskripsi.ui.component.CustomTimeTypeAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.sql.Date
import java.sql.Time

object RetrofitClient {

    private const val BASE_URL = "http://192.168.1.14:8000/" // Replace with the correct API URL

    // Create an instance of HttpLoggingInterceptor
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // Set logging level as needed
    }

    // Create an instance of OkHttpClient and add the interceptor
    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    // Create a custom Gson instance with the TimeTypeAdapter
    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(Time::class.java, CustomTimeTypeAdapter())
        .registerTypeAdapter(Date::class.java, CustomDateTypeAdapter())
        .create()

    // Create an instance of Retrofit with the configured OkHttpClient and Gson
    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient) // Add the configured client
            .addConverterFactory(GsonConverterFactory.create(gson)) // Use the custom Gson
            .build()
            .create(ApiService::class.java)
    }
}