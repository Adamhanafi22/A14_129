package com.example.projectakhir.ui.viewmodel.villa



import InsertVillaUiEvent
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir.model.Villa
import com.example.projectakhir.repository.VillaRepository
import com.example.projectakhir.ui.view.villa.DestinasiDetailVilla
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class DetailVillaUiState {
    data class Success(val villa: Villa) : DetailVillaUiState()
    object Error : DetailVillaUiState()
    object Loading : DetailVillaUiState()
}

class DetailVillaViewModel(
    savedStateHandle: SavedStateHandle,
    private val villaRepository: VillaRepository
) : ViewModel() {

    // Pastikan id_villa diambil sebagai String lalu dikonversi ke Int
    private val _idVilla: Int = savedStateHandle.get<String>(DestinasiDetailVilla.IDVILLA)?.toIntOrNull() ?: 0

    private val _detailUiState = MutableStateFlow<DetailVillaUiState>(DetailVillaUiState.Loading)
    val detailVillaUiState: StateFlow<DetailVillaUiState> = _detailUiState

    init {
        getDetailVilla()
    }

    fun getDetailVilla() {
        viewModelScope.launch {
            try {
                _detailUiState.value = DetailVillaUiState.Loading
                val villa = villaRepository.getVillaById(_idVilla)

                if (villa != null) {
                    _detailUiState.value = DetailVillaUiState.Success(villa)
                } else {
                    _detailUiState.value = DetailVillaUiState.Error
                }
            } catch (e: Exception) {
                _detailUiState.value = DetailVillaUiState.Error
            }
        }
    }
}

// Fungsi untuk mengonversi objek Villa ke InsertVillaUiEvent
fun Villa.toVillaDetailUiEvent(): InsertVillaUiEvent {
    return InsertVillaUiEvent(
        idVilla = idVilla,
        namaVilla = namaVilla,
        alamat = alamat,
        kamar_tersedia = kamar_tersedia
    )
}


