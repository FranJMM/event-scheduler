package com.martinezmencias.eventscheduler.testshared

import com.martinezmencias.eventscheduler.domain.DateAndTime
import com.martinezmencias.eventscheduler.domain.Event
import com.martinezmencias.eventscheduler.domain.Price
import com.martinezmencias.eventscheduler.domain.Venue
import java.util.*

val sampleVenue = Venue(
    id = "ID",
    name = "Name",
    city = "City",
    state = "State",
    country = "Country",
    address = "Address"
)

val samplePrice = Price(
    min = 0F,
    max = 1F,
    currency = "Currency"
)

val sampleEvent = Event(
    id = "ID",
    name = "Name",
    imageUrl = "URL",
    startDateAndTime = DateAndTime(date = "", time = ""),
    salesUrl = "URL",
    salesDateAndTime = DateAndTime(date = "", time = ""),
    venue = sampleVenue,
    price =  samplePrice,
    favorite = false
)




