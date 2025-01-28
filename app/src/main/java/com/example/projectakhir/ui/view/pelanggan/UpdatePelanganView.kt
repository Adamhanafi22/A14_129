package com.example.projectakhir.ui.view.pelanggan

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
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
import com.example.projectakhir.ui.viewmodel.pelanggan.UpdatePelangganViewModel
import com.example.projectakhir.ui.viewmodel.pelanggan.toPelanggan
import kotlinx.coroutines.launch


object DestinasiUpdatePelanggan : DestinasiNavigasi {
    override val route = "update_pelanggan"
    const val ID_PELANGGAN = "id_pelanggan"
    val routesWithArg = "$route/{$ID_PELANGGAN}"
    override val titleRes = "Update Pelanggan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePelangganView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdatePelangganViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val uiState = viewModel.uiState.value

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarr(
                title = DestinasiUpdatePelanggan.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            EntryBodyPelanggan(
                insertUiState = uiState,
                onPelangganValueChange = { updatedValue ->
                    viewModel.updatePelangganState(updatedValue)
                },
                onSaveClick = {
                    uiState.insertPelangganUiEvent?.let { insertPelangganUiEvent ->
                        coroutineScope.launch {
                            viewModel.updatePelanggan(
                                idPelanggan = viewModel.idPelanggan,
                                pelanggan = insertPelangganUiEvent.toPelanggan()
                            )
                            navigateBack()
                        }
                    }
                }
            )
        }
    }
}