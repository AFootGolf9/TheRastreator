package com.example.therastreator.data

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

class LocationJson (
    @SerializedName("latitude") val lat: Double?,
    @SerializedName("longitude") val long: Double?,
    @SerializedName("position_time") val time: Timestamp?
)