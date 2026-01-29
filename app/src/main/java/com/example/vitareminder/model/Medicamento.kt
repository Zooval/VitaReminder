package com.example.vitareminder.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Medicamento(
    val nombre: String,
    val tipo: String,
    val dosis: String,
    val horario: String
) : Parcelable