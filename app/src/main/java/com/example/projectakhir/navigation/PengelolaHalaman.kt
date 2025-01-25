package com.example.projectakhir.navigation



import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.projectakhir.ui.view.home.HomeScreenVilla
import com.example.projectakhir.ui.view.pelanggan.DaftarPelangganScreen
import com.example.projectakhir.ui.view.pelanggan.EntryPelangganScreen
import com.example.projectakhir.ui.view.reservasi.DaftarReservasiScreen
import com.example.projectakhir.ui.view.reservasi.InsertReservasiScreen
import com.example.projectakhir.ui.view.villa.DestinasiUpdate
import com.example.projectakhir.ui.view.villa.EntryVillaScreen
import com.example.projectakhir.ui.view.villa.DetailVillaScreen

@Composable
fun PengelolaHalaman(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "home",  // Start destination
        modifier = modifier,
    ) {
        // Halaman Home Pelanggan
        composable("Daftar Pel") {
            DaftarPelangganScreen(
                navigateToItemEntry = { navController.navigate("pelanggan_entry") },
                onDetailClick = { idPelanggan ->
                    navController.navigate("detail_pelanggan/$idPelanggan")
                }
            )
        }

        // Halaman Entry Pelanggan
        composable("pelanggan_entry") {
            EntryPelangganScreen(navigateBack = {
                navController.navigate("Daftar Pel") {
                    popUpTo("Daftar Pel") { inclusive = true }
                }
            })
        }


        // Halaman Home Villa
        composable("home") {
            HomeScreenVilla(
                navigateToItemEntry = { navController.navigate("entry_villa") },
                onDetailClick = { idVilla ->
                    navController.navigate("detail_villa/$idVilla")
                }
            )
        }

        // Halaman Entry Villa
        composable("entry_villa") {
            EntryVillaScreen(navigateBack = {
                navController.navigate("home") {
                    popUpTo("home") { inclusive = true }
                }
            })
        }

        // Halaman Detail Villa
        composable(
            route = "detail_villa/{id_villa}",
            arguments = listOf(navArgument("id_villa") { type = NavType.StringType })
        ) { backStackEntry ->
            val idVilla = backStackEntry.arguments?.getString("id_villa") ?: ""
            DetailVillaScreen(
                id_villa = idVilla,
                navigateBack = { navController.popBackStack() },
                onEditClick = { id ->
                    navController.navigate(DestinasiUpdate.routesWithArg.replace("{id_villa}", id))
                }
            )
        }

        // Halaman Daftar Reservasi
        composable("Daftar Reservasi") {
            DaftarReservasiScreen(
                navigateToItemEntry = { navController.navigate("reservasi_entry") },
                onDetailClick = { idVilla ->
                    navController.navigate("detail_villa/$idVilla")
                }
            )
        }

        // Halaman Entry Villa
        composable("reservasi_entry") {
            InsertReservasiScreen(navigateBack = {
                navController.navigate("Daftar Reservasi") {
                    popUpTo("Daftar Reservasi") { inclusive = true }
                }
            })
        }





    }

}
