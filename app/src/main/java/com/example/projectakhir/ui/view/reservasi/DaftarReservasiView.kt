package com.example.projectakhir.ui.view.reservasi


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir.model.Reservasi
import com.example.projectakhir.navigation.DestinasiNavigasi
import com.example.projectakhir.ui.viewmodel.reservasi.ReservasiUiState
import com.example.projectakhir.ui.viewmodel.reservasi.ReservasiViewModel
import com.example.projectakhir.R
import com.example.projectakhir.ui.customwidget.CustomBottomAppBar
import com.example.projectakhir.ui.viewmodel.PenyediaViewModel

object DestinasiDaftarReservasi : DestinasiNavigasi {
    override val route = "Daftar Reservasi"
    override val titleRes = "Daftar Reservasi"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DaftarReservasiScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},
    navigateToHome: () -> Unit = {},
    navigateToReview: () -> Unit = {},
    navigateToReservasi: () -> Unit = {},
    navigateToPelanggan: () -> Unit = {},
    onBackClick: () -> Unit = {},
    viewModel: ReservasiViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Data Reservasi") },
                actions = {
                    IconButton(onClick = { viewModel.getAllReservasi() }) {
                        Icon(Icons.Default.Add, contentDescription = "Refresh")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah Reservasi")
            }
        },
        bottomBar = {
            CustomBottomAppBar(
                onHomeClick = navigateToHome,
                onReviewClick = navigateToReview,
                onReservasiClick = navigateToReservasi,
                onPelangganClick = navigateToPelanggan,
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        ReservasiStatus(
            reservasiUiState = viewModel.reservasiUiState,
            retryAction = { viewModel.getAllReservasi() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deleteReservasi(it.id_reservasi)
                viewModel.getAllReservasi()
            }
        )
    }
}

@Composable
fun ReservasiStatus(
    reservasiUiState: ReservasiUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Reservasi) -> Unit = {},
    onDetailClick: (Int) -> Unit
) {
    when (reservasiUiState) {
        is ReservasiUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is ReservasiUiState.Success ->
            if (reservasiUiState.reservasi.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Reservasi")
                }
            } else {
                ReservasiList(
                    reservasi = reservasiUiState.reservasi,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.id_reservasi) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        is ReservasiUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ReservasiList(
    reservasi: List<Reservasi>,
    modifier: Modifier = Modifier,
    onDetailClick: (Reservasi) -> Unit,
    onDeleteClick: (Reservasi) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(reservasi) { reservasi ->
            ReservasiCard(
                reservasi = reservasi,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(reservasi) },
                onDeleteClick = { onDeleteClick(reservasi) }
            )
        }
    }
}

@Composable
fun ReservasiCard(
    reservasi: Reservasi,
    modifier: Modifier = Modifier,
    onDeleteClick: (Reservasi) -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Reservasi ID: ${reservasi.id_reservasi}",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(reservasi) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Hapus Reservasi"
                    )
                }
            }
            Text(text = "ID Villa: ${reservasi.id_villa}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Pelanggan ID: ${reservasi.id_pelanggan}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Check-In: ${reservasi.check_in}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Check-Out: ${reservasi.check_out}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Jumlah Kamar: ${reservasi.jumlah_kamar}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = "Error")
        Text(text = "Gagal Memuat Data", modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text("Coba Lagi")
        }
    }
}
