package com.martinezmencias.eventscheduler.data.server

import com.google.gson.annotations.SerializedName

data class RemoteResult(
    @SerializedName("_embedded") val embedded: RemoteEmbedded
)

data class RemoteEmbedded(
    val events: List<RemoteEvent>
)

data class RemoteEvent(val name: String, val images: List<RemoteImage>)

data class RemoteImage(val url: String)