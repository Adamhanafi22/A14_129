package com.example.projectakhir.model



import kotlinx.serialization.*

@Serializable
data class Review(
    val id_review: Int,  // Primary Key

    val id_reservasi: Int,  // Foreign Key to Reservasi

    val nilai: Int,  // Nilai review

    val komentar: String  // Komentar dari pelanggan
)

@Serializable
data class AllReviewResponse(
    val status: Boolean,
    val message: String,
    val data: List<Review>
)

@Serializable
data class ReviewDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Review
)
