package com.example.vitareminder

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Actividad(
    val nombre: String,
    val duracion: String,
    val frecuencia: String
) : Parcelable
