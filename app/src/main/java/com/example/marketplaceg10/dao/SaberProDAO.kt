package com.example.marketplaceg10.dao
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.marketplaceg10.model.Usuario
import com.example.marketplaceg10.model.Pregunta

@Dao
interface SaberProDAO {
    @Query ("Select * from usuario")
    fun getAllUsuarios(): LiveData<List<Usuario>>

    @Query ("Select * from pregunta")
    fun getAllPreguntas(): LiveData<List<Pregunta>>

    @Query ( "SELECT * FROM usuario WHERE id=:id")
    fun getUsuarioPorId(id: Int):Usuario

    @Query ( "SELECT * FROM pregunta WHERE id=:id")
    fun getPreguntaPorId(id: Int):Pregunta

    @Insert
    fun insertUsuario (usuario: Usuario)

    @Insert
    fun insertPregunta(pregunta: Pregunta)

    @Update
    fun  updateUsuario (usuario: Usuario)

    @Update
    fun  updatePregunta (pregunta: Pregunta)

    @Delete
    fun deleteUsuario (usuario: Usuario)

    @Delete
    fun deletePregunta(pregunta: Pregunta)
}