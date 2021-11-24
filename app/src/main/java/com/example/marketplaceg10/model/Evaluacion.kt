package com.example.marketplaceg10.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time
import java.time.DateTimeException
import java.util.*

@Entity(tableName ="evaluacion")
data class Evaluacion(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val Resultado:Int,
    val UserId:Int,
    val Fecha:String,
    val Hora:String
)
