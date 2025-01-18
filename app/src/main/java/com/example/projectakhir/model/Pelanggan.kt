package com.example.projectakhir.model


import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable
data class Pelanggan(
    val id_pelanggan: Int, // Primary Key

    val nama_pelanggan: String,

    val no_hp: Int
)

@Serializable
data class AllPelangganResponse(
    val status: Boolean,
    val message: String,
    val data: List<Pelanggan>
)

@Serializable
data class PelangganDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Pelanggan
)
