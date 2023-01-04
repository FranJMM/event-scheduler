package com.martinezmencias.eventscheduler.data.server

import retrofit2.http.*

interface RemoteService {

    @GET("events?locale=*&size=100")
    suspend fun requestEvents(
        @Query("apikey") apiKey: String,
        @Query("countryCode") countryCode: String,
        @Query("classificationName") classificationName: String,
    ): RemoteResult

}