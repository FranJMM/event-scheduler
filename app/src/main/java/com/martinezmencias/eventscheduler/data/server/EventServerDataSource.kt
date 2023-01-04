package com.martinezmencias.eventscheduler.data.server

import com.martinezmencias.eventscheduler.BuildConfig
import com.martinezmencias.eventscheduler.data.datasource.EventRemoteDataSource
import com.martinezmencias.eventscheduler.domain.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.converter.gson.GsonConverterFactory

class EventServerDataSource : EventRemoteDataSource {

    override suspend fun requestEvents(): List<Event> = withContext(Dispatchers.IO) {
        val remoteService = provideRemoteService()
        remoteService.requestEvents(
            apiKey = BuildConfig.API_KEY,
            countryCode = "ES",
            classificationName = "music"
        ).embedded.events.map {
            Event(
                name = it.name,
                image = it.images.first().url
            )
        }
    }

    private fun provideRemoteService(): RemoteService = provideRemoteService(BuildConfig.ENDPOINT)

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