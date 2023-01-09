package com.martinezmencias.eventscheduler.data.server

import com.google.gson.annotations.SerializedName

data class RemoteResult(
    @SerializedName("_embedded") val embedded: RemoteEmbedded
)

data class RemoteEmbedded(
    val events: List<RemoteEvent>
)

data class RemoteEvent(
    val id: String,
    val name: String,
    val images: List<RemoteImage>,
    val dates: Dates?,
    @SerializedName("url")  val salesUrl: String?,
    @SerializedName("sales") val salesDates: SalesDates?,
    @SerializedName("_embedded") val embeddedVenues: RemoteEmbeddedVenues?
)

data class RemoteImage(val url: String)

data class Dates(val start: Date?)

data class Date(@SerializedName("dateTime") val time: String)

data class SalesDates(@SerializedName("public") val publicSales: PublicSales?)

data class PublicSales(@SerializedName("startDateTime") val startTime: String)
