package com.example.projectakhir.model


import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable
data class Villa(
    @SerialName("id_villa")
    val idVilla: Int,

    @SerialName("nama_villa")
    val namaVilla: String,

    val alamat: String,

    val kamar_tersedia: Int = 0,
)

@Serializable
data class AllVillaResponse(
    val status: Boolean,
    val message: String,
    val data: List<Villa>
)

@Serializable
data class VillaDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Villa
)
