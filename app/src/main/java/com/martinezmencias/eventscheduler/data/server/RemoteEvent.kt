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
    @SerializedName("priceRanges")  val prices: List<RemotePrice>?,
    @SerializedName("url")  val salesUrl: String?,
    @SerializedName("sales") val salesDates: SalesDates?,
    @SerializedName("_embedded") val embeddedVenues: RemoteEmbeddedVenues?
)

data class RemoteImage(val url: String, val ratio: String, val width: Int, val height: Int)

data class Dates(val start: Start?)

data class Start(val localDate: String?, val localTime: String?)

data class SalesDates(@SerializedName("public") val publicSales: PublicSales?)

data class PublicSales(val startDateTime: String)

data class RemotePrice(val type: String, val min: Float, val max: Float, val currency: String)
