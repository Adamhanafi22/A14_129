package com.example.projectakhir.ui.view.pelanggan


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir.navigation.DestinasiNavigasi
import com.example.projectakhir.ui.customwidget.TopAppBarr
import com.example.projectakhir.ui.viewmodel.PenyediaViewModel
import com.example.projectakhir.ui.viewmodel.pelanggan.InsertPelangganUiEvent
import com.example.projectakhir.ui.viewmodel.pelanggan.InsertPelangganUiState
import com.example.projectakhir.ui.viewmodel.pelanggan.InsertPelangganViewModel

import kotlinx.coroutines.launch

object DestinasiPelangganEntry : DestinasiNavigasi {
    override val route = "pelanggan_entry"
    override val titleRes = "Entry Pelanggan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPelangganScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPelangganViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val context = LocalContext.current // Mengambil context dari Compose
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarr(
                title = DestinasiPelangganEntry.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBodyPelanggan(
            insertUiState = viewModel.uiState, // Menggunakan state dari ViewModel
            onPelangganValueChange = viewModel::updateInsertPelangganState, // Mengupdate state dari UI
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPelanggan(context) // Memanggil fungsi untuk insert pelanggan
                    navigateBack() // Navigasi kembali setelah insert
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBodyPelanggan(
    insertUiState: InsertPelangganUiState,
    onPelangganValueChange: (InsertPelangganUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputPelanggan(
            insertUiEventPelanggan = insertUiState.insertPelangganUiEvent,
            onValueChange = onPelangganValueChange,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputPelanggan(
    insertUiEventPelanggan: InsertPelangganUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertPelangganUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertUiEventPelanggan.nama_pelanggan,
            onValueChange = { onValueChange(insertUiEventPelanggan.copy(nama_pelanggan = it)) },
            label = { Text("Nama Pelanggan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertUiEventPelanggan.no_hp,
            onValueChange = { onValueChange(insertUiEventPelanggan.copy(no_hp = it))},
            label = { Text("Nomor HP") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
    }
}


