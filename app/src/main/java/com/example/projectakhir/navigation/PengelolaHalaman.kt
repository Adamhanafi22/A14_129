package com.example.projectakhir.navigation



import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.projectakhir.ui.view.home.DestinasiHome
import com.example.projectakhir.ui.view.home.HomeScreenVilla
import com.example.projectakhir.ui.view.pelanggan.DaftarPelangganScreen
import com.example.projectakhir.ui.view.pelanggan.DestinasiDaftarPelangan
import com.example.projectakhir.ui.view.pelanggan.DestinasiUpdatePelanggan
import com.example.projectakhir.ui.view.pelanggan.EntryPelangganScreen
import com.example.projectakhir.ui.view.pelanggan.UpdatePelangganView
import com.example.projectakhir.ui.view.reservasi.DaftarReservasiScreen
import com.example.projectakhir.ui.view.reservasi.DestinasiDaftarReservasi
import com.example.projectakhir.ui.view.reservasi.DetailReservasiScreen
import com.example.projectakhir.ui.view.reservasi.InsertReservasiScreen
import com.example.projectakhir.ui.view.review.DaftarReviewScreen
import com.example.projectakhir.ui.view.review.DestinasiDaftarReview
import com.example.projectakhir.ui.view.villa.DestinasiUpdate
import com.example.projectakhir.ui.view.villa.EntryVillaScreen
import com.example.projectakhir.ui.view.villa.DetailVillaScreen
import com.example.projectakhir.ui.view.villa.UpdateView

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
                onUpdateClick = { idPelanggan ->
                    navController.navigate(
                        DestinasiUpdatePelanggan.routesWithArg.replace(
                            "{${DestinasiUpdatePelanggan.ID_PELANGGAN}}", idPelanggan.toString()
                        )
                    )
                },

                navigateToReservasi = {navController.navigate(DestinasiDaftarReservasi.route)},
                navigateToPelanggan = {navController.navigate(DestinasiDaftarPelangan.route)},
                navigateToHome = {navController.navigate(DestinasiHome.route)},
                navigateToReview = {navController.navigate(DestinasiDaftarReview.route)},
                onBackClick =  {navController.navigate(DestinasiHome.route)}


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
        // update pelanggan
        composable(
            DestinasiUpdatePelanggan.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdatePelanggan.ID_PELANGGAN) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->


            val id_pel = backStackEntry.arguments?.getInt(DestinasiUpdatePelanggan.ID_PELANGGAN)

            id_pel?.let {
                UpdatePelangganView(
                    navigateBack = {
                        navController.popBackStack()
                    },

                    modifier = modifier,

                    )
            }
        }



        // Halaman Home Villa
        composable("home") {
            HomeScreenVilla(
                navigateToItemEntry = { navController.navigate("entry_villa") },
                onDetailClick = { idVilla ->
                    navController.navigate("detail_villa/$idVilla")
                },

                navigateToReservasi = {navController.navigate(DestinasiDaftarReservasi.route)},
                navigateToPelanggan = {navController.navigate(DestinasiDaftarPelangan.route)},
                navigateToHome = {navController.navigate(DestinasiHome.route)},
                navigateToReview = {navController.navigate(DestinasiDaftarReview.route)},


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
                onEditClick = {
                    navController.navigate(DestinasiUpdate.routesWithArg.replace("{${DestinasiUpdate.ID_VILLA}}", idVilla))
                },
                onReservasiClick = {
                    navController.navigate("Daftar Reservasi"

                        )

                }

            )
        }
        // Update Villa
        composable(
            DestinasiUpdate.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdate.ID_VILLA) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->


            val id_villa = backStackEntry.arguments?.getInt(DestinasiUpdate.ID_VILLA)

            id_villa?.let {
                UpdateView(
                    navigateBack = {
                        navController.popBackStack()
                    },

                    modifier = modifier,

                    )
            }
        }





        // Halaman Daftar Reservasi
        composable("Daftar Reservasi") {
            DaftarReservasiScreen(
                navigateToItemEntry = { navController.navigate("reservasi_entry") },
                onDetailClick = { idReservasi ->
                    navController.navigate("detail_reservasi/$idReservasi") // Perbaikan
                },
                navigateToReservasi = { navController.navigate(DestinasiDaftarReservasi.route) },
                navigateToPelanggan = { navController.navigate(DestinasiDaftarPelangan.route) },
                navigateToHome = { navController.navigate(DestinasiHome.route) },
                navigateToReview = { navController.navigate(DestinasiDaftarReview.route) },
                onBackClick = { navController.navigate(DestinasiHome.route) }
            )
        }

// Detail Reservasi
        composable(
            route = "detail_reservasi/{id_reservasi}",
            arguments = listOf(navArgument("id_reservasi") { type = NavType.StringType })
        ) { backStackEntry ->
            val idReservasi = backStackEntry.arguments?.getString("id_reservasi") ?: ""
            DetailReservasiScreen(
                id_reservasi = idReservasi,
                navigateBack = { navController.popBackStack() }
            )
        }



        // Halaman Entry Reservasi
        composable("reservasi_entry") {
            InsertReservasiScreen(navigateBack = {
                navController.navigate("Daftar Reservasi") {
                    popUpTo("Daftar Reservasi") { inclusive = true }
                }
            })
        }



        // Halaman Home Review
        composable("Daftar Rev") {
            DaftarReviewScreen(
                navigateToItemEntry = { navController.navigate("entry_villa") },

                navigateToReservasi = {navController.navigate(DestinasiDaftarReservasi.route)},
                navigateToPelanggan = {navController.navigate(DestinasiDaftarPelangan.route)},
                navigateToHome = {navController.navigate(DestinasiHome.route)},
                navigateToReview = {navController.navigate(DestinasiDaftarReview.route)},
                onBackClick =  {navController.navigate(DestinasiHome.route)}

                )
        }






    }

}
