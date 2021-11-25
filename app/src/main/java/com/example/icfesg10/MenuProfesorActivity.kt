package com.example.icfesg10

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.icfesg10.database.saberProDB
import com.example.icfesg10.model.Pregunta
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
        var  pregunta: Pregunta = Pregunta( 0, "", "" , "","","","")

        CoroutineScope( Dispatchers.IO ).launch {
            //obtener la instancia de la BDs
            val database = context?.let { saberProDB.getDatabase(it) }

            //consultamos la pelicula x ID en la BDs
            pregunta = database?.SaberProDAO()?.getPreguntaPorId(idPregunta)!!

            /*val edtTitulo = fragmento.findViewById<EditText>( R.id.edtTitulo)
            val edtDuracion = fragmento.findViewById<EditText>( R.id.edtDuracion)
            val edtProtagonista = fragmento.findViewById<EditText>( R.id.edtProtagonista)
            edtTitulo.setText( pelicula.titulo )
            edtDuracion.setText( pelicula.duracion.toString() )
            edtProtagonista.setText( pelicula.protagonista )*/
        }
        salir()
    }

        private fun salir(){
            activity?.supportFragmentManager?.beginTransaction()
                ?.remove( this )
                ?.commit()
        }
}

