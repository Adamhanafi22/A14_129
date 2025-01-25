package com.example.projectakhir.ui.view.pelanggan

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir.R
import com.example.projectakhir.model.Pelanggan
import com.example.projectakhir.navigation.DestinasiNavigasi
import com.example.projectakhir.ui.viewmodel.PenyediaViewModel
import com.example.projectakhir.ui.viewmodel.home.HomeViewModel
import com.example.projectakhir.ui.viewmodel.pelanggan.PelangganUiState
import com.example.projectakhir.ui.viewmodel.pelanggan.PelangganViewModel

object DestinasiDaftarPelangan: DestinasiNavigasi {
    override val route ="Daftar Pel"
    override val titleRes = "Daftar Pelanggan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DaftarPelangganScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},
    viewModel: PelangganViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Data Pelanggan") },
                actions = {
                    IconButton(onClick = { viewModel.getAllPelanggan() }) {
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
                Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah Pelanggan")
            }
        }
    ) { innerPadding ->
        PelangganStatus(
            pelangganUiState = viewModel.pelangganUiState,
            retryAction = { viewModel.getAllPelanggan() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deletePelanggan(it.id_pelanggan)
                viewModel.getAllPelanggan()
            }
        )
    }
}

@Composable
fun PelangganStatus(
    pelangganUiState: PelangganUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Pelanggan) -> Unit = {},
    onDetailClick: (Int) -> Unit
) {
    when (pelangganUiState) {
        is PelangganUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is PelangganUiState.Success ->
            if (pelangganUiState.pelanggan.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Pelanggan")
                }
            } else {
                PelangganList(
                    pelanggan = pelangganUiState.pelanggan,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.id_pelanggan) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        is PelangganUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun PelangganList(
    pelanggan: List<Pelanggan>,
    modifier: Modifier = Modifier,
    onDetailClick: (Pelanggan) -> Unit,
    onDeleteClick: (Pelanggan) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(pelanggan) { pelanggan ->
            PelangganCard(
                pelanggan = pelanggan,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(pelanggan) },
                onDeleteClick = { onDeleteClick(pelanggan) }
            )
        }
    }
}

@Composable
fun PelangganCard(
    pelanggan: Pelanggan,
    modifier: Modifier = Modifier,
    onDeleteClick: (Pelanggan) -> Unit = {}
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
                    text = pelanggan.nama_pelanggan,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(pelanggan) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Hapus Pelanggan"
                    )
                }
            }
            Text(text = "ID: ${pelanggan.id_pelanggan}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "No HP: ${pelanggan.no_hp}", style = MaterialTheme.typography.bodyMedium)
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
