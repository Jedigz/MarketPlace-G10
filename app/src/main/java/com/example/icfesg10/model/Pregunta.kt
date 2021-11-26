package com.example.icfesg10.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pregunta")
data class Pregunta(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val PreTexto: String,
    val Opcion1: String,
    val Opcion2: String,
    val Opcion3: String,
    val Respuesta: String,
    val Area: String,
    val Descripcion: String

)
