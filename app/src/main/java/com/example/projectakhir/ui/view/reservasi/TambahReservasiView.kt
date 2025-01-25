package com.example.projectakhir.ui.view.reservasi

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir.model.Pelanggan
import com.example.projectakhir.navigation.DestinasiNavigasi
import com.example.projectakhir.ui.customwidget.TopAppBarr
import com.example.projectakhir.ui.viewmodel.PenyediaViewModel
import com.example.projectakhir.ui.viewmodel.reservasi.InsertReservasiViewModel
import com.example.projectakhir.ui.viewmodel.reservasi.ReservasiUiEvent
import kotlinx.coroutines.launch
import java.util.*

object ReservasiEntry : DestinasiNavigasi {
    override val route = "reservasi_entry"
    override val titleRes = "Entry Reservasi"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertReservasiScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertReservasiViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val context = LocalContext.current

    // Observasi state flow menggunakan collectAsState()
    val reservasiUiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarr(
                title = ReservasiEntry.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryReservasiBody(
            reservasiUiState = reservasiUiState.insertUiEvent,
            onReservasiValueChange = viewModel::updateReservasiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertReservasi(context)
                    navigateBack()
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
fun EntryReservasiBody(
    reservasiUiState: ReservasiUiEvent,
    onReservasiValueChange: (ReservasiUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormReservasiInput(
            reservasiUiEvent = reservasiUiState,
            onValueChange = onReservasiValueChange,
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
fun FormReservasiInput(
    reservasiUiEvent: ReservasiUiEvent,
    onValueChange: (ReservasiUiEvent) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val dateFormatter = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }
    var showDatePicker by remember { mutableStateOf(false) }
    var datePickerType by remember { mutableStateOf("") } // "check_in" atau "check_out"
    var expanded by remember { mutableStateOf(false) }
    var selectedPelanggan by remember { mutableStateOf<Pelanggan?>(null) }

    // List pelanggan diambil dari ViewModel atau parameter
    val pelangganList = listOf<Pelanggan>() // Gantilah dengan data yang sesuai

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = reservasiUiEvent.id_villa.toString(),
            onValueChange = { onValueChange(reservasiUiEvent.copy(id_villa = it.toIntOrNull() ?: 0)) },
            label = { Text("ID Villa") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        // Perbaiki dropdown tanpa mengubah fungsionalitas fokus
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            OutlinedTextField(
                value = selectedPelanggan?.nama_pelanggan ?: "Pilih Pelanggan",
                onValueChange = {},
                label = { Text("Nama Pelanggan") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true },  // Klik untuk membuka dropdown
                enabled = false,
                singleLine = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                }
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                pelangganList.forEach { pelanggan ->
                    DropdownMenuItem(
                        text = { Text(pelanggan.nama_pelanggan) },
                        onClick = {
                            selectedPelanggan = pelanggan
                            onValueChange(reservasiUiEvent.copy(id_pelanggan = pelanggan.id_pelanggan))
                            expanded = false
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = dateFormatter.format(reservasiUiEvent.check_in),
            onValueChange = {},
            label = { Text("Check-in Date") },
            modifier = Modifier.fillMaxWidth().clickable {
                datePickerType = "check_in"
                showDatePicker = true
            },
            enabled = false,
            singleLine = true
        )

        OutlinedTextField(
            value = dateFormatter.format(reservasiUiEvent.check_out),
            onValueChange = {},
            label = { Text("Check-out Date") },
            modifier = Modifier.fillMaxWidth().clickable {
                datePickerType = "check_out"
                showDatePicker = true
            },
            enabled = false,
            singleLine = true
        )

        OutlinedTextField(
            value = reservasiUiEvent.jumlah_kamar.toString(),
            onValueChange = { onValueChange(reservasiUiEvent.copy(jumlah_kamar = it.toIntOrNull() ?: 0)) },
            label = { Text("Jumlah Kamar") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
    }
}
