package com.martinezmencias.eventscheduler.data.datasource

interface LocationDataSource {
    suspend fun findLastRegion(): String?
}