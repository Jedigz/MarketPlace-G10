package com.example.icfesg10.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.icfesg10.dao.SaberProDAO
import com.example.icfesg10.model.Evaluacion
import com.example.icfesg10.model.Pregunta
import com.example.icfesg10.model.Usuario

@Database (entities=[Usuario::class,Pregunta::class, Evaluacion::class], version = 1)

abstract class saberProDB :RoomDatabase(){

        abstract fun SaberProDAO() :  SaberProDAO

        companion object{
            @Volatile
            private var INSTANCE: saberProDB?= null

            fun getDatabase(context: Context): saberProDB {
                val tempInstancia = INSTANCE

                if (tempInstancia != null){
                    return tempInstancia
                }
                else{
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        saberProDB::class.java,
                        "app_database"
                    ).build()
                    INSTANCE =instance
                    return instance
                }
            }

        }

}