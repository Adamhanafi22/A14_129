package com.example.projectakhir.repository


import com.example.projectakhir.model.AllReservasiResponse
import com.example.projectakhir.model.Reservasi
import com.example.projectakhir.service_api.ReservasiService
import java.io.IOException

interface ReservasiRepository {

    suspend fun getAllReservasi(): AllReservasiResponse

    suspend fun insertReservasi(reservasi: Reservasi)

    suspend fun updateReservasi(id_reservasi: Int, reservasi: Reservasi)

    suspend fun deleteReservasi(id_reservasi: Int)

    suspend fun getReservasiById(id_reservasi: Int): Reservasi
}

class NetworkReservasiRepository(
    private val reservasiApiService: ReservasiService
) : ReservasiRepository {

    // Mengambil daftar semua reservasi
    override suspend fun getAllReservasi(): AllReservasiResponse =
        reservasiApiService.getAllReservasi()

    // Menambahkan reservasi baru
    override suspend fun insertReservasi(reservasi: Reservasi) {
        reservasiApiService.insertReservasi(reservasi)
    }

    // Memperbarui data reservasi berdasarkan id_reservasi
    override suspend fun updateReservasi(id_reservasi: Int, reservasi: Reservasi) {
        reservasiApiService.updateReservasi(id_reservasi, reservasi)
    }

    // Menghapus reservasi berdasarkan id_reservasi
    override suspend fun deleteReservasi(id_reservasi: Int) {
        try {
            val response = reservasiApiService.deleteReservasi(id_reservasi)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete Reservasi. HTTP Status Code: ${response.code()}"
                )
            } else {
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    // Mengambil reservasi berdasarkan id_reservasi
    override suspend fun getReservasiById(id_reservasi: Int): Reservasi {
        return reservasiApiService.getReservasiById(id_reservasi).data
    }
}
