package com.example.projectakhir.ui.viewmodel.pelanggan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.io.IOException
import coil.network.HttpException
import com.example.projectakhir.model.Pelanggan
import com.example.projectakhir.repository.PelangganRepository

sealed class PelangganUiState {
    data class Success(val pelanggan: List<Pelanggan>) : PelangganUiState()
    object Error : PelangganUiState()
    object Loading : PelangganUiState()
}

class PelangganViewModel(private val pelangganRepository: PelangganRepository) : ViewModel() {
    var pelangganUiState: PelangganUiState by mutableStateOf(PelangganUiState.Loading)
        private set

    init {
        getAllPelanggan()
    }

    fun getAllPelanggan() {
        viewModelScope.launch {
            pelangganUiState = PelangganUiState.Loading
            pelangganUiState = try {
                PelangganUiState.Success(pelangganRepository.getAllPelanggan().data)
            } catch (e: IOException) {
                PelangganUiState.Error
            } catch (e: HttpException) {
                PelangganUiState.Error
            }
        }
    }

    fun deletePelanggan(id_pelanggan: Int) {
        viewModelScope.launch {
            try {
                pelangganRepository.deletePelanggan(id_pelanggan)
            } catch (e: IOException) {
                PelangganUiState.Error
            } catch (e: HttpException) {
                PelangganUiState.Error
            }
        }
    }
}
