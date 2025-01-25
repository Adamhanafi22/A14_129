package com.example.projectakhir.ui.viewmodel.reservasi

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir.model.Pelanggan
import com.example.projectakhir.model.Reservasi
import com.example.projectakhir.model.Villa
import com.example.projectakhir.repository.PelangganRepository
import com.example.projectakhir.repository.ReservasiRepository
import com.example.projectakhir.repository.VillaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.util.Date

class InsertReservasiViewModel(
    private val reservasiRepository: ReservasiRepository,
    private val pelangganRepository: PelangganRepository,
    private val villaRepository: VillaRepository
) : ViewModel() {

    var uiState = MutableStateFlow(InsertUiState())
        private set

    private val _pelangganList = MutableStateFlow<List<Pelanggan>>(emptyList())
    val pelangganList: StateFlow<List<Pelanggan>> get() = _pelangganList

    private val _villaList = MutableStateFlow<List<Villa>>(emptyList())
    val villaList: StateFlow<List<Villa>> get() = _villaList

    init {
        fetchPelanggan()
        fetchVilla()
    }

    private fun fetchPelanggan() {
        viewModelScope.launch {
            try {
                val response = pelangganRepository.getAllPelanggan()
                _pelangganList.value = response.data // Ambil hanya bagian `data`
            } catch (e: HttpException) {
                Log.e("Error", "HTTP Exception: ${e.message}")
            } catch (e: Exception) {
                Log.e("Error", "Exception: ${e.message}")
            }
        }
    }

    private fun fetchVilla() {
        viewModelScope.launch {
            try {
                val response = villaRepository.getAllVilla()
                _villaList.value = response.data // Ambil hanya bagian `data`
            } catch (e: HttpException) {
                Log.e("Error", "HTTP Exception: ${e.message}")
            } catch (e: Exception) {
                Log.e("Error", "Exception: ${e.message}")
            }
        }
    }

    fun updateReservasiState(insertUiEvent: ReservasiUiEvent) {
        uiState.value = InsertUiState(insertUiEvent = insertUiEvent)
    }

    fun insertReservasi(context: Context) {
        viewModelScope.launch {
            try {
                reservasiRepository.insertReservasi(uiState.value.insertUiEvent.toReservasi())
                Toast.makeText(context, "Reservasi berhasil disimpan", Toast.LENGTH_LONG).show()
            } catch (e: HttpException) {
                Log.e("Error", "HTTP Exception: ${e.message()}")
                Toast.makeText(context, "HTTP Error: ${e.message()}", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                Log.e("Error", "General Exception: ${e.message}")
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}

data class InsertUiState(
    val insertUiEvent: ReservasiUiEvent = ReservasiUiEvent()
)

data class ReservasiUiEvent(
    val id_villa: Int = 0,
    val id_pelanggan: Int = 0,
    val check_in: Date = Date(),
    val check_out: Date = Date(),
    val jumlah_kamar: Int = 0
)

fun ReservasiUiEvent.toReservasi(): Reservasi = Reservasi(
    id_reservasi = 0,
    id_villa = id_villa,
    id_pelanggan = id_pelanggan,
    check_in = check_in,
    check_out = check_out,
    jumlah_kamar = jumlah_kamar
)
