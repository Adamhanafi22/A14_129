package com.example.projectakhir.service_api



import com.example.projectakhir.model.AllReviewResponse
import com.example.projectakhir.model.Review
import com.example.projectakhir.model.ReviewDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ReviewService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    // Menambahkan review baru
    @POST("store")
    suspend fun insertReview(@Body review: Review)

    // Mengambil semua review
    @GET("review/")
    suspend fun getAllReviews(): AllReviewResponse

    // Mengambil detail review berdasarkan id
    @GET("{id_review}")
    suspend fun getReviewById(@Path("id_review") id_review: Int): ReviewDetailResponse

    // Memperbarui data review berdasarkan id_review
    @PUT("{id_review}")
    suspend fun updateReview(
        @Path("id_review") id_review: Int,
        @Body review: Review
    ): Response<Void>
}
