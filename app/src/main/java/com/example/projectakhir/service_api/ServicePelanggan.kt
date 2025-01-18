package com.example.projectakhir.service_api


import com.example.projectakhir.model.AllPelangganResponse
import com.example.projectakhir.model.Pelanggan
import com.example.projectakhir.model.PelangganDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PelangganService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    // Menambahkan pelanggan baru
    @POST("store")
    suspend fun insertPelanggan(@Body pelanggan: Pelanggan)

    // Mengambil semua data pelanggan
    @GET(".")
    suspend fun getAllPelanggan(): AllPelangganResponse

    // Mengambil detail pelanggan berdasarkan id
    @GET("{id_pelanggan}")
    suspend fun getPelangganById(@Path("id_pelanggan") id_pelanggan: Int): PelangganDetailResponse

    // Memperbarui data pelanggan berdasarkan id_pelanggan
    @PUT("{id_pelanggan}")
    suspend fun updatePelanggan(
        @Path("id_pelanggan") id_pelanggan: Int,
        @Body pelanggan: Pelanggan
    ): Response<Void>

    // Menghapus pelanggan berdasarkan id_pelanggan
    @DELETE("{id_pelanggan}")
    suspend fun deletePelanggan(@Path("id_pelanggan") id_pelanggan: Int): Response<Void>
}
