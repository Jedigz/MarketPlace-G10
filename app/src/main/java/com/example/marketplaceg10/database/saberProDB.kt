package com.example.marketplaceg10.database

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.marketplaceg10.dao.SaberProDAO
import com.example.marketplaceg10.model.Evaluacion
import com.example.marketplaceg10.model.Pregunta
import com.example.marketplaceg10.model.Usuario
import java.security.AccessControlContext

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