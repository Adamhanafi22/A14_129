package com.example.projectakhir.ui.view.villa


import InsertVillaUiEvent
import InsertVillaUiState
import InsertVillaViewModel
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir.navigation.DestinasiNavigasi
import com.example.projectakhir.ui.customwidget.TopAppBarr
import com.example.projectakhir.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiEntry: DestinasiNavigasi {
    override val route = "item_entry"
    override val titleRes = "Entry Villa"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryVillaScreen(
    navigateBack:()->Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertVillaViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarr(
                title = DestinasiEntry.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBody(
            insertVillaUiState = viewModel.uiState,
            onVillaValueChange = viewModel::updateInsertVillaState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertVilla()
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
fun EntryBody(
    insertVillaUiState: InsertVillaUiState,
    onVillaValueChange:(InsertVillaUiEvent)->Unit,
    onSaveClick:()->Unit,
    modifier: Modifier =Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            InsertVillaUiEvent = insertVillaUiState.insertVillaUiEvent,
            onValueChange = onVillaValueChange,
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

fun FormInput(
    InsertVillaUiEvent : InsertVillaUiEvent,
    modifier: Modifier =Modifier,
    onValueChange:(InsertVillaUiEvent)->Unit = {},
    enabled: Boolean = true
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = InsertVillaUiEvent.idVilla.toString(),  // Konversi ke String jika idVilla adalah Int
            onValueChange = { onValueChange(InsertVillaUiEvent.copy(idVilla = it.toIntOrNull() ?: 0)) },
            label = { Text("Id Villa") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = InsertVillaUiEvent.namaVilla,
            onValueChange = {onValueChange(InsertVillaUiEvent.copy(namaVilla = it))},
            label = { Text("Nama Villa") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )


        OutlinedTextField(
            value = InsertVillaUiEvent.alamat,
            onValueChange = {onValueChange(InsertVillaUiEvent.copy(alamat = it))},
            label = { Text("Alamat") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = InsertVillaUiEvent.kamar_tersedia.toString(),  // Pastikan input berupa string yang valid
            onValueChange = { newValue ->
                val newKamarTersedia = newValue.toIntOrNull()
                if (newKamarTersedia != null && newKamarTersedia > 0) {
                    // Jika valid, set value pada state
                    onValueChange(InsertVillaUiEvent.copy(kamar_tersedia = newKamarTersedia))
                } else {
                    // Tampilkan pesan error jika nilai tidak valid
                    Log.e("InsertVilla", "Invalid value for kamar_tersedia: $newValue")
                    // Bisa menampilkan pesan kesalahan pada UI
                }
            },
            label = { Text("Kamar Tersedia") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        if (enabled){
            Text(
                text = "Isi Semua Data!",
                modifier = Modifier.padding(12.dp)
            )
        }

        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )


    }
}