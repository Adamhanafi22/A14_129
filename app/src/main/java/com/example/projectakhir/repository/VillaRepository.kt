package com.example.projectakhir.repository


import com.example.projectakhir.model.AllVillaResponse
import com.example.projectakhir.model.Villa
import com.example.projectakhir.service_api.VillaService
import java.io.IOException

interface VillaRepository {

    suspend fun getAllVillas(): AllVillaResponse

    suspend fun insertVilla(villa: Villa)

    suspend fun updateVilla(id_villa: Int, villa: Villa)

    suspend fun deleteVilla(id_villa: Int)

    suspend fun getVillaById(id_villa: Int): Villa
}

class NetworkVillaRepository(
    private val villaApiService: VillaService
) : VillaRepository {

    // Mengambil daftar semua villa
    override suspend fun getAllVillas(): AllVillaResponse =
        villaApiService.getAllVillas()

    // Menambahkan villa baru
    override suspend fun insertVilla(villa: Villa) {
        villaApiService.insertVilla(villa)
    }

    // Memperbarui data villa berdasarkan id_villa
    override suspend fun updateVilla(id_villa: Int, villa: Villa) {
        villaApiService.updateVilla(id_villa, villa)
    }

    // Menghapus villa berdasarkan id_villa
    override suspend fun deleteVilla(id_villa: Int) {
        try {
            val response = villaApiService.deleteVilla(id_villa)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete Villa. HTTP Status Code: ${response.code()}"
                )
            } else {
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    // Mengambil villa berdasarkan id_villa
    override suspend fun getVillaById(id_villa: Int): Villa {
        return villaApiService.getVillaById(id_villa).data
    }
}
