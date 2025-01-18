package com.example.projectakhir.container


import com.example.projectakhir.repository.*
import com.example.projectakhir.service_api.*
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val villaRepository: VillaRepository
    val reviewRepository: ReviewRepository
    val reservasiRepository: ReservasiRepository
    val pelangganRepository: PelangganRepository
}

class VillaContainer : AppContainer {
    private val baseUrl = "http://10.0.2.2:3000/api/"
    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val villaService: VillaService by lazy {
        retrofit.create(VillaService::class.java)
    }

    private val reviewService: ReviewService by lazy {
        retrofit.create(ReviewService::class.java)
    }

    private val reservasiService: ReservasiService by lazy {
        retrofit.create(ReservasiService::class.java)
    }

    private val pelangganService: PelangganService by lazy {
        retrofit.create(PelangganService::class.java)
    }

    override val villaRepository: VillaRepository by lazy {
        NetworkVillaRepository(villaService)
    }

    override val reviewRepository: ReviewRepository by lazy {
        NetworkReviewRepository(reviewService)
    }

    override val reservasiRepository: ReservasiRepository by lazy {
        NetworkReservasiRepository(reservasiService)
    }

    override val pelangganRepository: PelangganRepository by lazy {
        NetworkPelangganRepository(pelangganService)
    }
}
