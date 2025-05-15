package com.antoniowalls.indriverstudents.domain.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializer

data class DirectionsResponse (
    @SerializedName("routes")
    val routes: List<Route>
)

data class Route(
    @SerializedName("overview_polyline")
    val overviewPolyline: OverviewPolyline
)

data class OverviewPolyline(
    @SerializedName("points")
    val points: String
)