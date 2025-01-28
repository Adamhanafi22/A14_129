package com.example.projectakhir.ui.view.reservasi

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir.model.Reservasi
import com.example.projectakhir.navigation.DestinasiNavigasi
import com.example.projectakhir.ui.customwidget.TopAppBarr
import com.example.projectakhir.ui.view.home.OnError
import com.example.projectakhir.ui.view.home.OnLoading
import com.example.projectakhir.ui.viewmodel.PenyediaViewModel
import com.example.projectakhir.ui.viewmodel.reservasi.DetailReservasiViewModell
import com.example.projectakhir.ui.viewmodel.reservasi.DetailReservasitate
import java.text.SimpleDateFormat
import java.util.Locale

object DestinasiDetailReservasi : DestinasiNavigasi {
    override val route = "detail_reservasi"
    const val ID_RESERVASI = "id_reservasi"
    val routesWithArg = "$route/{$ID_RESERVASI}"
    override val titleRes = "Detail Reservasi"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailReservasiScreen(
    id_reservasi: String,
    modifier: Modifier = Modifier,
    viewModel: DetailReservasiViewModell = viewModel(factory = PenyediaViewModel.Factory),
    navigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBarr(
                title = DestinasiDetailReservasi.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = { viewModel.getDetailReservasi() }
            )
        }
    ) { innerPadding ->
        val detailReservasiUiState by viewModel.detailReservasiaUiState.collectAsState()

        BodyDetailReservasi(
            modifier = Modifier.padding(innerPadding),
            detailReservasiUiState = detailReservasiUiState,
            retryAction = { viewModel.getDetailReservasi() }
        )
    }
}

@Composable
fun BodyDetailReservasi(
    modifier: Modifier = Modifier,
    detailReservasiUiState: DetailReservasitate,
    retryAction: () -> Unit = {}
) {
    when (detailReservasiUiState) {
        is DetailReservasitate.Loading -> {
            OnLoading(modifier = modifier.fillMaxSize())
        }
        is DetailReservasitate.Success -> {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailReservasi(reservasi = detailReservasiUiState.reservasi)
            }
        }
        is DetailReservasitate.Error -> {
            OnError(
                retryAction = retryAction,
                modifier = modifier.fillMaxSize()
            )
        }
        else -> {
            Text("Unexpected state encountered")
        }
    }
}

@Composable
fun ItemDetailReservasi(
    modifier: Modifier = Modifier,
    reservasi: Reservasi
) {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailReservasi(judul = "ID Reservasi", isinya = reservasi.id_reservasi.toString())
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailReservasi(judul = "ID Villa", isinya = reservasi.id_villa.toString())
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailReservasi(judul = "ID Pelanggan", isinya = reservasi.id_pelanggan.toString())
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailReservasi(judul = "Check-in", isinya = dateFormat.format(reservasi.check_in))
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailReservasi(judul = "Check-out", isinya = dateFormat.format(reservasi.check_out))
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailReservasi(judul = "Jumlah Kamar", isinya = reservasi.jumlah_kamar.toString())
        }
    }
}

@Composable
fun ComponentDetailReservasi(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = isinya, fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}
