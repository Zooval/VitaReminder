package com.example.vitareminder.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cita(
    val doctor: String,
    val fecha: String,
    val hora: String
) : Parcelable