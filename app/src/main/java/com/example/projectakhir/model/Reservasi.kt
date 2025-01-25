package com.example.projectakhir.model

import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor


import kotlinx.serialization.*
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.text.SimpleDateFormat
import java.util.Date

// Custom Serializer for Date
object DateSerializer : KSerializer<Date> {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd") // Set your date format here

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Date", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Date {
        val dateStr = decoder.decodeString()
        return dateFormat.parse(dateStr) ?: throw SerializationException("Invalid date format")
    }

    override fun serialize(encoder: Encoder, value: Date) {
        val dateStr = dateFormat.format(value)
        encoder.encodeString(dateStr)
    }
}

@Serializable
data class Reservasi(
    val id_reservasi: Int,  // Primary Key

    val id_villa: Int,  // Foreign Key

    val id_pelanggan: Int,  // Foreign Key

    @Serializable(with = DateSerializer::class)
    val check_in: Date,  // Tanggal check-in

    @Serializable(with = DateSerializer::class)
    val check_out: Date,  // Tanggal check-out

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

