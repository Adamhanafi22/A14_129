package com.example.projectakhir.repository


import com.example.projectakhir.model.AllReviewResponse
import com.example.projectakhir.model.Review
import com.example.projectakhir.service_api.ReviewService
import java.io.IOException

interface ReviewRepository {

    suspend fun getAllReviews(): AllReviewResponse

    suspend fun insertReview(review: Review)

    suspend fun updateReview(id_review: Int, review: Review)

    suspend fun getReviewById(id_review: Int): Review
}

class NetworkReviewRepository(
    private val reviewApiService: ReviewService
) : ReviewRepository {

    // Mengambil daftar semua review
    override suspend fun getAllReviews(): AllReviewResponse =
        reviewApiService.getAllReviews()

    // Menambahkan review baru
    override suspend fun insertReview(review: Review) {
        reviewApiService.insertReview(review)
    }

    // Memperbarui data review berdasarkan id_review
    override suspend fun updateReview(id_review: Int, review: Review) {
        reviewApiService.updateReview(id_review, review)
    }

    // Mengambil review berdasarkan id_review
    override suspend fun getReviewById(id_review: Int): Review {
        return reviewApiService.getReviewById(id_review).data
    }
}
