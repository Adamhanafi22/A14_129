package com.example.projectakhir.ui.viewmodel



import InsertVillaViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.projectakhir.VillaApplications
import com.example.projectakhir.ui.viewmodel.home.HomeViewModel
import com.example.projectakhir.ui.viewmodel.pelanggan.InsertPelangganViewModel
import com.example.projectakhir.ui.viewmodel.pelanggan.PelangganViewModel
import com.example.projectakhir.ui.viewmodel.pelanggan.UpdatePelangganViewModel
import com.example.projectakhir.ui.viewmodel.reservasi.DetailReservasiViewModell
import com.example.projectakhir.ui.viewmodel.reservasi.InsertReservasiViewModel
import com.example.projectakhir.ui.viewmodel.reservasi.ReservasiViewModel
import com.example.projectakhir.ui.viewmodel.review.ReviewViewModel
import com.example.projectakhir.ui.viewmodel.villa.DetailVillaViewModel
import com.example.projectakhir.ui.viewmodel.villa.UpdateVillaViewModel


object PenyediaViewModel {
    val Factory = viewModelFactory {
        // Villa
        initializer { HomeViewModel(aplikasiVilla().container.villaRepository) }
        initializer { InsertVillaViewModel(aplikasiVilla().container.villaRepository) }
        initializer {
            DetailVillaViewModel(
                createSavedStateHandle(),
                aplikasiVilla().container.villaRepository
            )
        }
        initializer {
            UpdateVillaViewModel(
                createSavedStateHandle(),
                aplikasiVilla().container.villaRepository
            )
        }


        // Pelangan
        initializer { PelangganViewModel(aplikasiVilla().container.pelangganRepository) }
        initializer { InsertPelangganViewModel(aplikasiVilla().container.pelangganRepository) }
        initializer { UpdatePelangganViewModel(createSavedStateHandle(), aplikasiVilla().container.pelangganRepository)
        }



        // Reservasi
        initializer {
            InsertReservasiViewModel(
                aplikasiVilla().container.reservasiRepository,
                aplikasiVilla().container.pelangganRepository,
                aplikasiVilla().container.villaRepository
            )
        }
        initializer { ReservasiViewModel(aplikasiVilla().container.reservasiRepository) }
        initializer { DetailReservasiViewModell(createSavedStateHandle(),aplikasiVilla().container.reservasiRepository) }

        //Review
        initializer { ReviewViewModel(aplikasiVilla().container.reviewRepository) }

    }


}

fun CreationExtras.aplikasiVilla():VillaApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as VillaApplications)