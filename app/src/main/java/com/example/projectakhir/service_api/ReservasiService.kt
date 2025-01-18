package com.example.projectakhir.service_api


import com.example.projectakhir.model.AllReservasiResponse
import com.example.projectakhir.model.Reservasi
import com.example.projectakhir.model.ReservasiDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ReservasiService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    // Menambahkan reservasi baru
    @POST("store")
    suspend fun insertReservasi(@Body reservasi: Reservasi)

    // Mengambil semua reservasi
    @GET(".")
    suspend fun getAllReservasi(): AllReservasiResponse

    // Mengambil detail reservasi berdasarkan id
    @GET("{id_reservasi}")
    suspend fun getReservasiById(@Path("id_reservasi") id_reservasi: Int): ReservasiDetailResponse

    // Memperbarui data reservasi berdasarkan id_reservasi
    @PUT("{id_reservasi}")
    suspend fun updateReservasi(
        @Path("id_reservasi") id_reservasi: Int,
        @Body reservasi: Reservasi
    ): Response<Void>

    // Menghapus reservasi berdasarkan id_reservasi
    @DELETE("{id_reservasi}")
    suspend fun deleteReservasi(@Path("id_reservasi") id_reservasi: Int): Response<Void>
}
