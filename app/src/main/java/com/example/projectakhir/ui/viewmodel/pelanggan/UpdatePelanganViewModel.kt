package com.example.projectakhir.ui.viewmodel.pelanggan
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir.model.Pelanggan
import com.example.projectakhir.repository.PelangganRepository
import com.example.projectakhir.ui.view.pelanggan.DestinasiUpdatePelanggan
import com.example.projectakhir.ui.view.villa.DestinasiUpdate
import kotlinx.coroutines.launch


class UpdatePelangganViewModel(
    savedStateHandle: SavedStateHandle,
    private val pelangganRepository: PelangganRepository
) : ViewModel() {

    // Mengambil ID Pelanggan dari SavedStateHandle
    val idPelanggan: Int = checkNotNull(savedStateHandle[DestinasiUpdatePelanggan.ID_PELANGGAN])

    var uiState = mutableStateOf(InsertPelangganUiState())
        private set

    init {
        ambilPelanggan()
    }

    // Mengambil data pelanggan berdasarkan ID
    private fun ambilPelanggan() {
        viewModelScope.launch {
            try {
                val pelanggan = pelangganRepository.getPelangganById(idPelanggan)
                pelanggan?.let {
                    uiState.value = it.toUiStatePelanggan() // Update state dengan data yang diambil
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Fungsi untuk mengupdate data pelanggan
    fun updatePelanggan(idPelanggan: Int, pelanggan: Pelanggan) {
        viewModelScope.launch {
            try {
                pelangganRepository.updatePelanggan(idPelanggan, pelanggan)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Memperbarui UI state dengan InsertPelangganUiEvent baru
    fun updatePelangganState(insertUiEvent: InsertPelangganUiEvent) {
        uiState.value = uiState.value.copy(insertPelangganUiEvent = insertUiEvent)
    }
}



