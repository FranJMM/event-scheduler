package com.martinezmencias.eventscheduler.data.server

data class RemoteEmbeddedVenues(
    val venues: List<RemoteVenue>?
)


data class RemoteVenue(
    val id: String,
    val name: String,
    val city: City?,
    val state: State?,
    val country: Country?,
    val address: Address?
)

data class City(val name: String)

data class State(val name: String)

data class Country(val name: String)

data class Address(val line1: String)