package com.example.projectakhir.ui.viewmodel.reservasi


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.io.IOException
import coil.network.HttpException
import com.example.projectakhir.model.Reservasi
import com.example.projectakhir.repository.ReservasiRepository

sealed class ReservasiUiState {
    data class Success(val reservasi: List<Reservasi>) : ReservasiUiState()
    object Error : ReservasiUiState()
    object Loading : ReservasiUiState()
}


class ReservasiViewModel(private val reservasiRepository: ReservasiRepository) : ViewModel() {
    var reservasiUiState: ReservasiUiState by mutableStateOf(ReservasiUiState.Loading)
        private set

    init {
        getAllReservasi()
    }

    fun getAllReservasi() {
        viewModelScope.launch {
            reservasiUiState = ReservasiUiState.Loading
            reservasiUiState = try {
                ReservasiUiState.Success(reservasiRepository.getAllReservasi().data)
            } catch (e: IOException) {
                ReservasiUiState.Error
            } catch (e: HttpException) {
                ReservasiUiState.Error
            }
        }
    }

    fun deleteReservasi(id_reservasi: Int) {
        viewModelScope.launch {
            try {
                reservasiRepository.deleteReservasi(id_reservasi)
            } catch (e: IOException) {
                reservasiUiState = ReservasiUiState.Error
            } catch (e: HttpException) {
                reservasiUiState = ReservasiUiState.Error
            }
        }
    }
}