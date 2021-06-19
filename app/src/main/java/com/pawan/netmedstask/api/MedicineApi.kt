package com.pawan.netmedstask.api

import com.pawan.netmedstask.models.Medicine
import com.pawan.netmedstask.models.MedicineResponse
import retrofit2.Response
import retrofit2.http.GET

interface MedicineApi {

    @GET("/api/medicines")
    suspend fun getMedicines(

    ): Response<MedicineResponse>
}