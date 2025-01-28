package com.example.projectakhir.ui.viewmodel.reservasi



import InsertVillaUiEvent
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir.model.Reservasi
import com.example.projectakhir.model.Villa
import com.example.projectakhir.repository.ReservasiRepository
import com.example.projectakhir.repository.VillaRepository
import com.example.projectakhir.ui.view.reservasi.DestinasiDetailReservasi
import com.example.projectakhir.ui.view.villa.DestinasiDetailVilla
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class DetailReservasitate {
    data class Success(val reservasi: Reservasi) : DetailReservasitate()
    object Error : DetailReservasitate()
    object Loading : DetailReservasitate()

}

class DetailReservasiViewModell(
    savedStateHandle: SavedStateHandle,
    private val reservasiRepository: ReservasiRepository
) : ViewModel() {


    private val _idRes: Int = savedStateHandle.get<String>(DestinasiDetailReservasi.ID_RESERVASI)?.toIntOrNull() ?: 0

    private val _detailUiState = MutableStateFlow<DetailReservasitate>(DetailReservasitate.Loading)
    val detailReservasiaUiState: StateFlow<DetailReservasitate> = _detailUiState

    init {
        getDetailReservasi()
    }

    fun getDetailReservasi() {
        viewModelScope.launch {
            try {
                _detailUiState.value = DetailReservasitate.Loading
                val reservasi = reservasiRepository.getReservasiById(_idRes)

                if (reservasi != null) {
                    _detailUiState.value = DetailReservasitate.Success(reservasi)
                } else {
                    _detailUiState.value = DetailReservasitate.Error
                }
            } catch (e: Exception) {
                _detailUiState.value = DetailReservasitate.Error
            }
        }
    }
}