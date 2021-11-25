package com.example.icfesg10

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import com.example.icfesg10.database.saberProDB
import com.example.icfesg10.model.Pregunta
import kotlinx.android.synthetic.main.fragment_preguntas.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PreguntasFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmento = inflater.inflate(R.layout.fragment_preguntas, container, false)

        val btnGuardar = fragmento.findViewById<Button>( R.id.btnGuardar)

        btnGuardar.setOnClickListener {
            val context = activity?.applicationContext

            //Instanciamos un objeto pelicula par Guardar en la BD
            val pregunta =  Pregunta( 0, "${edtTexto.text}", "${edtopcion1.text}", "${edtopcion2.text}","${edtOpcion3.text}","${edtRespuesta.text}","${edtarea.text}")

            //insertamos en la BDs utilizando una Coroutine
            CoroutineScope( Dispatchers.IO ).launch {
                val database = context?.let{ saberProDB.getDatabase( it ) }
                if ( database != null){
                    database.SaberProDAO().insertPregunta( pregunta )
                }
            }

            salir()
        }

        val btnCancelar = fragmento.findViewById<Button>( R.id.btnCancelar)
        btnCancelar.setOnClickListener {
            salir()
        }

        return fragmento
    }

    private fun salir(){
        val lvPreguntas = activity?.findViewById<ListView>( R.id.lvpreguntas )
        lvPreguntas?.visibility = View.VISIBLE

        activity?.supportFragmentManager?.beginTransaction()
            ?.remove( this )
            ?.commit()
    }

}