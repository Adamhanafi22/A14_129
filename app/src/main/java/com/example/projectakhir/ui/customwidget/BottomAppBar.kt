package com.example.projectakhir.ui.customwidget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBottomAppBar(
    onHomeClick: () -> Unit = {},
    onReviewClick: () -> Unit = {},     // Ganti Profile → Review
    onPelangganClick: () -> Unit = {},
    onReservasiClick: () -> Unit = {},  // Ganti Add Data → Reservasi
    onBackClick: () -> Unit = {},
    isBackEnabled: Boolean = true,
    isHomeEnabled: Boolean = true,
    isReservasiEnabled: Boolean = true, // Ganti Add Data → Reservasi
    isReviewEnabled: Boolean = true,    // Ganti Profile → Review
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentPadding = PaddingValues(horizontal = 16.dp),
        tonalElevation = 4.dp
    ) {
        // Back Button
        if (isBackEnabled) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        // Spacer
        if (isBackEnabled) {
            Spacer(modifier = Modifier.weight(1f))
        }

        // Home Button
        if (isHomeEnabled) {
            IconButton(onClick = onHomeClick) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Home",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        // Spacer
        if (isHomeEnabled) {
            Spacer(modifier = Modifier.weight(1f))
        }

        // Reservasi Button (Search Icon)
        if (isReservasiEnabled) {
            IconButton(onClick = onReservasiClick) {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Reservasi",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        // Spacer
        if (isReservasiEnabled) {
            Spacer(modifier = Modifier.weight(1f))
        }

        // Review Button (Person Icon)
        if (isReviewEnabled) {
            IconButton(onClick = onReviewClick) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Review",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
        // Spacer
        if (isReservasiEnabled) {
            Spacer(modifier = Modifier.weight(1f))
        }

        // Review Button (Person Icon)
        if (isReviewEnabled) {
            IconButton(onClick = onPelangganClick) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Review",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}
