package com.example.projectakhir.ui.viewmodel.home

import coil.network.HttpException
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir.model.Villa
import com.example.projectakhir.repository.VillaRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeUiState {
    data class Success(val villa: List<Villa>) : HomeUiState()
    object  Error : HomeUiState()
    object Loading : HomeUiState()
}

class HomeViewModel (private val vla: VillaRepository): ViewModel(){
    var mhsUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getAllVilla()
    }

    fun getAllVilla(){
        viewModelScope.launch {
            mhsUIState = HomeUiState.Loading
            mhsUIState = try {
                HomeUiState.Success(vla.getAllVilla().data)
            }catch (e: IOException){
                HomeUiState.Error
            }catch ( e: HttpException){
                HomeUiState.Error
            }
        }
    }

    fun deleteVilla(id_villa:Int){
        viewModelScope.launch {
            try {
                vla.deleteVilla(id_villa)
            }catch (e: IOException){
                HomeUiState.Error
            }catch ( e:HttpException){
                HomeUiState.Error
            }
        }
    }
}