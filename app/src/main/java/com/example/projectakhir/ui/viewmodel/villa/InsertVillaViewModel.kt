import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir.model.Villa
import com.example.projectakhir.repository.VillaRepository
import kotlinx.coroutines.launch

class InsertVillaViewModel(private val villaRepository: VillaRepository) : ViewModel() {

    // State untuk memanage UI terkait Insert Villa
    var uiState by mutableStateOf(InsertVillaUiState())
        private set

    // Update state berdasarkan perubahan UI Event
    fun updateInsertVillaState(insertVillaUiEvent: InsertVillaUiEvent) {
        uiState = InsertVillaUiState(insertVillaUiEvent = insertVillaUiEvent)
    }

    fun isValidData(): Boolean {
        return uiState.insertVillaUiEvent.namaVilla.isNotEmpty() &&
                uiState.insertVillaUiEvent.alamat.isNotEmpty() &&
                uiState.insertVillaUiEvent.kamar_tersedia > 0
    }

    // Fungsi untuk insert data Villa
    fun insertVilla() {
        if (isValidData()) {
            viewModelScope.launch {
                try {
                    uiState = uiState.copy(isLoading = true) // Menandakan loading saat insert
                    villaRepository.insertVilla(uiState.insertVillaUiEvent.toVilla())
                    uiState = uiState.copy(isLoading = false, isSuccess = true) // Set success
                } catch (e: Exception) {
                    Log.e("InsertVillaViewModel", "Error inserting villa: ${e.localizedMessage}", e)
                    if (e is retrofit2.HttpException) {
                        try {
                            val errorBody = e.response()?.errorBody()?.string()
                            Log.e("InsertVillaViewModel", "Error response body: $errorBody")
                        } catch (exception: Exception) {
                            Log.e("InsertVillaViewModel", "Error parsing error body", exception)
                        }
                    }
                    uiState = uiState.copy(
                        isLoading = false,
                        errorMessage = e.localizedMessage ?: "Terjadi kesalahan"
                    )
                }
            }
        } else {
            uiState = uiState.copy(errorMessage = "Data tidak valid!")
        }
    }
}

// State untuk Insert Villa UI
data class InsertVillaUiState(
    val insertVillaUiEvent: InsertVillaUiEvent = InsertVillaUiEvent(),
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String = ""
)

// Event untuk menangani perubahan form
data class InsertVillaUiEvent(
    val idVilla: Int = 0,
    val namaVilla: String = "",
    val alamat: String = "",
    val kamar_tersedia: Int = 0
)

// Fungsi untuk mengubah event menjadi model Villa
fun InsertVillaUiEvent.toVilla(): Villa = Villa(
    idVilla = idVilla,
    namaVilla = namaVilla,
    alamat = alamat,
    kamar_tersedia = kamar_tersedia
)

// Fungsi untuk mengubah model Villa menjadi UI State
fun Villa.toUiStateVilla(): InsertVillaUiState = InsertVillaUiState(
    insertVillaUiEvent = toInsertVillaUiEvent()
)

// Fungsi untuk mengubah model Villa menjadi InsertVillaUiEvent
fun Villa.toInsertVillaUiEvent(): InsertVillaUiEvent = InsertVillaUiEvent(
    idVilla = idVilla,
    namaVilla = namaVilla,
    alamat = alamat,
    kamar_tersedia = kamar_tersedia
)

