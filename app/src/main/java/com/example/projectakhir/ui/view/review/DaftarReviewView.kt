package com.example.projectakhir.ui.view.review

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
import com.example.projectakhir.model.Review
import com.example.projectakhir.navigation.DestinasiNavigasi
import com.example.projectakhir.ui.customwidget.CustomBottomAppBar
import com.example.projectakhir.ui.viewmodel.PenyediaViewModel
import com.example.projectakhir.ui.viewmodel.home.HomeViewModel
import com.example.projectakhir.ui.viewmodel.pelanggan.PelangganUiState
import com.example.projectakhir.ui.viewmodel.pelanggan.PelangganViewModel
import com.example.projectakhir.ui.viewmodel.review.ReviewUiState
import com.example.projectakhir.ui.viewmodel.review.ReviewViewModel

object DestinasiDaftarReview: DestinasiNavigasi {
    override val route ="Daftar Rev"
    override val titleRes = "Daftar Review"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DaftarReviewScreen(
    modifier: Modifier = Modifier,
    navigateToItemEntry: () -> Unit,
    onDetailClick: (Int) -> Unit = {},
    navigateToHome: () -> Unit = {},
    navigateToReview: () -> Unit = {},
    navigateToReservasi: () -> Unit = {},
    navigateToPelanggan: () -> Unit = {},
    onBackClick: () -> Unit = {},
    viewModel: ReviewViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Daftar Review") },
                actions = {
                    IconButton(onClick = { viewModel.getAllReview() }) {
                        Icon(Icons.Default.Add, contentDescription = "Refresh")
                    }
                }
            )
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
        ReviewStatus(
            reviewUiState = viewModel.reviewUiState,
            retryAction = { viewModel.getAllReview() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick
        )
    }
}


@Composable
fun ReviewStatus(
    reviewUiState: ReviewUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit
) {
    when (reviewUiState) {
        is ReviewUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is ReviewUiState.Success -> {
            if (reviewUiState.review.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada review")
                }
            } else {
                ReviewList(
                    reviews = reviewUiState.review,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.id_review) }
                )
            }
        }
        is ReviewUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ReviewList(
    reviews: List<Review>,
    modifier: Modifier = Modifier,
    onDetailClick: (Review) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(reviews) { review ->
            ReviewCard(
                review = review,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(review) }
            )
        }
    }
}

@Composable
fun ReviewCard(
    review: Review,
    modifier: Modifier = Modifier
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
            Text(
                text = review.komentar,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(text = "Rating: ${review.komentar}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Tanggal: ${review.nilai}", style = MaterialTheme.typography.bodyMedium)
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
