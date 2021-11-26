package com.example.marketplaceg10

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import com.example.marketplaceg10.database.saberProDB
import com.example.marketplaceg10.model.Pregunta
import kotlinx.android.synthetic.main.activity_preguntas.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MenuProfesorActivity : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmento = inflater.inflate(R.layout.fragment_detalle_pregunta, container, false)

        val context = activity?.applicationContext
        val idpregunta = requireArguments().getInt("idPregunta")

        verpregunta(fragmento, idpregunta)

        return fragmento
    }


    private fun verpregunta( fragmento: View, idPregunta: Int) {
        var  pregunta: Pregunta = Pregunta( 0, "", "" , "","","","","")

        CoroutineScope( Dispatchers.IO ).launch {
            //obtener la instancia de la BDs
            val database = context?.let { saberProDB.getDatabase(it) }

            //consultamos la pelicula x ID en la BDs
            pregunta = database?.SaberProDAO()?.getPreguntaPorId(idPregunta)!!

            val edtArea = fragmento.findViewById<EditText>( R.id.edtarea)
            val edtDescripcion = fragmento.findViewById<EditText>( R.id.edtDescripcion)
            edtArea.setText( pregunta.Area)
            edtDescripcion.setText( pregunta.Descripcion.toString() )

        }
        salir()
    }

        private fun salir(){
            activity?.supportFragmentManager?.beginTransaction()
                ?.remove( this )
                ?.commit()
        }
}

