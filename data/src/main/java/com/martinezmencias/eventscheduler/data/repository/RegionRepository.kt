package com.martinezmencias.eventscheduler.data.repository

import com.martinezmencias.eventscheduler.data.PermissionChecker
import com.martinezmencias.eventscheduler.data.PermissionChecker.Permission.COARSE_LOCATION
import com.martinezmencias.eventscheduler.data.datasource.LocationDataSource

class RegionRepository(
    private val locationDataSource: LocationDataSource,
    private val permissionChecker: PermissionChecker
) {

    companion object {
        const val DEFAULT_REGION = "US"
    }

    suspend fun findLastRegion(): String {
        return if (permissionChecker.check(COARSE_LOCATION)) {
            locationDataSource.findLastRegion() ?: DEFAULT_REGION
        } else {
            DEFAULT_REGION
        }
    }
}