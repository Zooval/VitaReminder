package com.example.vitareminder

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cita(
    val doctor: String,
    val fecha: String,
    val hora: String
) : Parcelable
