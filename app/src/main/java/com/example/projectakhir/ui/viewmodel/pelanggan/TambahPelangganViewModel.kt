package com.example.projectakhir.ui.viewmodel.pelanggan


import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir.model.Pelanggan
import com.example.projectakhir.repository.PelangganRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class InsertPelangganViewModel(private val pl: PelangganRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertPelangganUiState())
        private set

    fun updateInsertPelangganState(insertUiEvent: InsertPelangganUiEvent) {
        uiState = uiState.copy(insertPelangganUiEvent = insertUiEvent)
    }

    fun insertPelanggan(context: Context) {
        viewModelScope.launch {
            try {
                // Memanggil fungsi untuk menyimpan data pelanggan
                pl.insertPelanggan(uiState.insertPelangganUiEvent.toPelanggan())
                Toast.makeText(context, "Pelanggan berhasil disimpan", Toast.LENGTH_LONG).show()
            } catch (e: HttpException) {
                // Tangani exception HTTP (misal: 500 Internal Server Error)
                Log.e("InsertPelangganViewModel", "HTTP Exception: ${e.message()}", e)
                // Tampilkan pesan error kepada user
                if (e.code() == 500) {
                    Toast.makeText(context, "Internal Server Error. Please try again later.", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "HTTP Error: ${e.message()}", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                // Tangani general exception
                Log.e("InsertPelangganViewModel", "General Exception: ${e.message}", e)
                Toast.makeText(context, "An unexpected error occurred: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}




data class InsertUiState(
    val insertUiEvent: InsertPelangganUiEvent = InsertPelangganUiEvent()
)

// State untuk Insert Pelanggan UI
data class InsertPelangganUiState(
    val insertPelangganUiEvent: InsertPelangganUiEvent = InsertPelangganUiEvent(),
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String = ""
)

// Event untuk menangani perubahan form Pelanggan
data class InsertPelangganUiEvent(
    val id_pelanggan: Int = 0,
    val nama_pelanggan: String = "",
    val no_hp: String = ""
)

// Fungsi untuk mengubah event menjadi model Pelanggan
fun InsertPelangganUiEvent.toPelanggan(): Pelanggan = Pelanggan(
    id_pelanggan = id_pelanggan,
    nama_pelanggan = nama_pelanggan,
    no_hp = no_hp
)

// Fungsi untuk mengubah model Pelanggan menjadi UI State
fun Pelanggan.toUiStatePelanggan(): InsertPelangganUiState = InsertPelangganUiState(
    insertPelangganUiEvent = toInsertPelangganUiEvent()
)

// Fungsi untuk mengubah model Pelanggan menjadi InsertPelangganUiEvent
fun Pelanggan.toInsertPelangganUiEvent(): InsertPelangganUiEvent = InsertPelangganUiEvent(
    id_pelanggan = id_pelanggan,
    nama_pelanggan = nama_pelanggan,
    no_hp = no_hp
)


