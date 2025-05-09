package com.antoniowalls.indriverstudents.domain.repository

import com.google.android.gms.maps.model.LatLng
import okhttp3.Call

interface LocationRepository {
    fun getLocationUpdates(call: (position: LatLng) -> Unit)
}