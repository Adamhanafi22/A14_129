package com.example.projectakhir.service_api


import com.example.projectakhir.model.AllVillaResponse
import com.example.projectakhir.model.Villa
import com.example.projectakhir.model.VillaDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface VillaService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    // Menambahkan villa baru
    @POST("villa/store")
    suspend fun insertVilla(@Body villa: Villa)

    // Mengambil semua villa
    @GET("villa/")
    suspend fun getAllVilla(): AllVillaResponse

    // Mengambil detail villa berdasarkan id
    @GET("villa/{id_villa}")
    suspend fun getVillaById(@Path("id_villa") id_villa: Int): VillaDetailResponse

    // Memperbarui data villa berdasarkan id_villa
    @PUT("{id_villa}")
    suspend fun updateVilla(
        @Path("id_villa") id_villa: Int,
        @Body villa: Villa
    ): Response<Villa>

    // Menghapus villa berdasarkan id_villa
    @DELETE("villa/{id_villa}")
    suspend fun deleteVilla(@Path("id_villa") id_villa: Int): Response<Void>
}
