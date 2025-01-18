package com.example.projectakhir.model

import kotlinx.serialization.*

@Serializable
data class Reservasi(
    val id_reservasi: Int,  // Primary Key

    val id_villa: Int,  // Foreign Key

    val id_pelanggan: Int,  // Foreign Key

    val check_in: String,  // Tanggal check-in

    val check_out: String,  // Tanggal check-out

    val jumlah_kamar: Int
)

@Serializable
data class AllReservasiResponse(
    val status: Boolean,
    val message: String,
    val data: List<Reservasi>
)

@Serializable
data class ReservasiDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Reservasi
)
