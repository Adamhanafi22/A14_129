package com.example.projectakhir.ui.view.villa


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir.R
import com.example.projectakhir.model.Villa
import com.example.projectakhir.navigation.DestinasiNavigasi
import com.example.projectakhir.ui.customwidget.TopAppBarr
import com.example.projectakhir.ui.view.home.OnError
import com.example.projectakhir.ui.view.home.OnLoading
import com.example.projectakhir.ui.viewmodel.PenyediaViewModel
import com.example.projectakhir.ui.viewmodel.villa.DetailVillaUiState
import com.example.projectakhir.ui.viewmodel.villa.DetailVillaViewModel

object DestinasiDetailVilla : DestinasiNavigasi {
    override val route = "detail_villa"
    const val IDVILLA = "id_villa"
    val routesWithArg = "$route/{$IDVILLA}"
    override val titleRes = "Detail Villa"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailVillaScreen(
    id_villa: String,
    modifier: Modifier = Modifier,
    viewModel: DetailVillaViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onEditClick: (String) -> Unit = {},
    navigateBack: () -> Unit,
    onReservasiClick: (String) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBarr(
                title = DestinasiDetailVilla.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = { viewModel.getDetailVilla() } // Refresh data villa
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(id_villa) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Villa"
                )
            }
        }
    ) { innerPadding ->
        val detailVillaUiState by viewModel.detailVillaUiState.collectAsState()

        BodyDetailVilla(
            modifier = Modifier.padding(innerPadding),
            detailVillaUiState = detailVillaUiState,
            retryAction = { viewModel.getDetailVilla() },
            onReservasiClick = onReservasiClick
        )
    }
}

@Composable
fun BodyDetailVilla(
    modifier: Modifier = Modifier,
    detailVillaUiState: DetailVillaUiState,
    retryAction: () -> Unit = {},
    onReservasiClick: (String) -> Unit = {}
) {
    when (detailVillaUiState) {
        is DetailVillaUiState.Loading -> {
            OnLoading(modifier = modifier.fillMaxSize())
        }
        is DetailVillaUiState.Success -> {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailVilla(villa = detailVillaUiState.villa, onReservasiClick = onReservasiClick)
            }
        }
        is DetailVillaUiState.Error -> {
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
fun ItemDetailVilla(
    modifier: Modifier = Modifier,
    villa: Villa,
    onReservasiClick: (String) -> Unit = {}
) {
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
            Image(
                painter = painterResource(R.drawable.villa), // Gambar dari drawable
                contentDescription = "Villa Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            ComponentDetailVilla(judul = "ID Villa", isinya = villa.idVilla.toString())
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailVilla(judul = "Nama Villa", isinya = villa.namaVilla)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailVilla(judul = "Alamat", isinya = villa.alamat)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailVilla(judul = "Kamar Tersedia", isinya = villa.kamar_tersedia.toString())
            Spacer(modifier = Modifier.padding(4.dp))

            Button(
                onClick = { onReservasiClick(villa.idVilla.toString()) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Reservasi")
            }
        }
    }
}

@Composable
fun ComponentDetailVilla(
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


