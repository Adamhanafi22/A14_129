package com.example.projectakhir.ui.view.villa

import InsertVillaUiState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir.navigation.DestinasiNavigasi
import com.example.projectakhir.ui.customwidget.TopAppBarr
import com.example.projectakhir.ui.viewmodel.PenyediaViewModel
import com.example.projectakhir.ui.viewmodel.villa.UpdateVillaViewModel
import kotlinx.coroutines.launch
import toVilla

object DestinasiUpdate : DestinasiNavigasi {
    override val route = "update"
    const val ID_VILLA = "id_villa"
    val routesWithArg = "$route/{$ID_VILLA}"
    override val titleRes = "Update Villa"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateVillaViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()


    val uiState = viewModel.uiState.value

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarr(
                title = DestinasiUpdate.titleRes,
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
            EntryBody(
                insertVillaUiState = uiState,
                onVillaValueChange = { updatedValue ->
                    viewModel.updateVillaState(updatedValue)
                },
                onSaveClick = {
                    uiState.insertVillaUiEvent?.let { insertVillaUiEvent ->
                        coroutineScope.launch {
                            viewModel.updateVilla(
                                idVilla = viewModel.idVilla,
                                villa = insertVillaUiEvent.toVilla()
                            )
                            navigateBack()
                        }
                    }
                }
            )
        }
    }
}