package com.example.projectakhir.ui.viewmodel.villa

import InsertVillaUiEvent
import InsertVillaUiState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir.model.Villa
import com.example.projectakhir.repository.VillaRepository
import com.example.projectakhir.ui.view.villa.DestinasiUpdate
import kotlinx.coroutines.launch
import toInsertVillaUiEvent

class UpdateVillaViewModel(
    savedStateHandle: SavedStateHandle,
    private val villaRepository: VillaRepository
) : ViewModel() {

    // Mengambil ID Villa dari SavedStateHandle
    val idVilla: Int = checkNotNull(savedStateHandle[DestinasiUpdate.ID_VILLA])

    var uiState = mutableStateOf(InsertVillaUiState())
        private set

    init {
        ambilVilla()
    }

    // Mengambil data villa berdasarkan ID
    private fun ambilVilla() {
        viewModelScope.launch {
            try {
                val villa = villaRepository.getVillaById(idVilla)
                villa?.let {
                    uiState.value = it.toUiStateVilla() // Update state dengan data yang diambil
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Fungsi untuk mengupdate data villa
    fun updateVilla(idVilla: Int, villa: Villa) {
        viewModelScope.launch {
            try {
                villaRepository.updateVilla(idVilla, villa)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Memperbarui UI state dengan InsertVillaUiEvent baru
    fun updateVillaState(insertUiEvent: InsertVillaUiEvent) {
        uiState.value = uiState.value.copy(insertVillaUiEvent = insertUiEvent)
    }
}

// Fungsi konversi dari Villa ke InsertVillaUiState
fun Villa.toUiStateVilla(): InsertVillaUiState = InsertVillaUiState(
    insertVillaUiEvent = this.toInsertVillaUiEvent()
)
