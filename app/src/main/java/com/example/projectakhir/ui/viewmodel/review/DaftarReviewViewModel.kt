package com.example.projectakhir.ui.viewmodel.review


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.io.IOException
import coil.network.HttpException
import com.example.projectakhir.model.Pelanggan
import com.example.projectakhir.model.Review
import com.example.projectakhir.repository.PelangganRepository
import com.example.projectakhir.repository.ReviewRepository

sealed class ReviewUiState {
    data class Success(val review: List<Review>) : ReviewUiState()
    object Error : ReviewUiState()
    object Loading : ReviewUiState()
}

class ReviewViewModel(private val reviewRepository: ReviewRepository) : ViewModel() {
    var reviewUiState: ReviewUiState by mutableStateOf(ReviewUiState.Loading)
        private set

    init {
        getAllReview()
    }

    fun getAllReview() {
        viewModelScope.launch {
            reviewUiState = ReviewUiState.Loading
            reviewUiState = try {
                ReviewUiState.Success(reviewRepository.getAllReviews().data)
            } catch (e: IOException) {
                ReviewUiState.Error
            } catch (e: HttpException) {
                ReviewUiState.Error
            }
        }
    }

}
