package com.martinezmencias.eventscheduler.data.server

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.converter.gson.GsonConverterFactory

interface EventRemoteDataSource {
    suspend fun requestEvents(): List<RemoteEvent>
}

class EventServerDataSource : EventRemoteDataSource {

    override suspend fun requestEvents(): List<RemoteEvent> = withContext(Dispatchers.IO) {
        val remoteService = provideRemoteService()
        remoteService.requestEvents(
            apiKey = "",
            countryCode = "ES",
            classificationName = "music"
        ).embedded.events
    }

    private fun provideRemoteService(): RemoteService = provideRemoteService("")

    private inline fun <reified T> provideRemoteService(baseUrl: String): T = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(provideOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create()

    private fun provideOkHttpClient(): OkHttpClient {
        val logginInterceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        return OkHttpClient.Builder().addInterceptor(logginInterceptor).build()
    }
}