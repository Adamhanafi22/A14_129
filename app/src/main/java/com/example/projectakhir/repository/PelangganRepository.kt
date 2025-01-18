package com.example.projectakhir.repository


import com.example.projectakhir.model.AllPelangganResponse
import com.example.projectakhir.model.Pelanggan
import com.example.projectakhir.service_api.PelangganService
import java.io.IOException

interface PelangganRepository {

    suspend fun getAllPelanggan(): AllPelangganResponse

    suspend fun insertPelanggan(pelanggan: Pelanggan)

    suspend fun updatePelanggan(id_pelanggan: Int, pelanggan: Pelanggan)

    suspend fun deletePelanggan(id_pelanggan: Int)

    suspend fun getPelangganById(id_pelanggan: Int): Pelanggan
}

class NetworkPelangganRepository(
    private val pelangganApiService: PelangganService
) : PelangganRepository {

    // Mengambil daftar semua pelanggan
    override suspend fun getAllPelanggan(): AllPelangganResponse =
        pelangganApiService.getAllPelanggan()

    // Menambahkan pelanggan baru
    override suspend fun insertPelanggan(pelanggan: Pelanggan) {
        pelangganApiService.insertPelanggan(pelanggan)
    }

    // Memperbarui data pelanggan berdasarkan id_pelanggan
    override suspend fun updatePelanggan(id_pelanggan: Int, pelanggan: Pelanggan) {
        pelangganApiService.updatePelanggan(id_pelanggan, pelanggan)
    }

    // Menghapus pelanggan berdasarkan id_pelanggan
    override suspend fun deletePelanggan(id_pelanggan: Int) {
        try {
            val response = pelangganApiService.deletePelanggan(id_pelanggan)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete Pelanggan. HTTP Status Code: ${response.code()}"
                )
            } else {
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    // Mengambil pelanggan berdasarkan id_pelanggan
    override suspend fun getPelangganById(id_pelanggan: Int): Pelanggan {
        return pelangganApiService.getPelangganById(id_pelanggan).data
    }
}
