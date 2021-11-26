package com.example.marketplaceg10

import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import com.example.marketplaceg10.database.saberProDB
import com.example.marketplaceg10.databinding.FragmentPreguntasBinding
import com.example.marketplaceg10.model.Pregunta
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_preguntas.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.google.firebase.ktx.initialize
import java.util.*


class PreguntasFragment : Fragment() {
    private lateinit var binding: FragmentPreguntasBinding


    val database = Firebase.database
    val dbReferencePreguntas =database.getReference("preguntas")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
   /*     binding =FragmentPreguntasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Firebase.initialize(this)

        binding.btnGuardar.setOnClickListener{
            guardarPreguntas()
        }*/

    }
/*
    private fun guardarPreguntas() {
        var pregunta = Pregunta(
            UUID.randomUUID().toString(),
            binding.edtTexto.toString(),
            binding.edtopcion1.toString(),
            binding.edtopcion2.toString(),
            binding.edtOpcion3.toString(),
            binding.edtRespuesta.toString(),
        )
      dbReferencePreguntas.child(pregunta.id)
    }
*/



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmento = inflater.inflate(R.layout.fragment_preguntas, container, false)

        val btnGuardar = fragmento.findViewById<Button>( R.id.btnGuardar)

        btnGuardar.setOnClickListener {
            val context = activity?.applicationContext

            //Instanciamos un objeto pelicula par Guardar en la BD
            val pregunta =  Pregunta( 0, "${edtTexto.text}", "${edtopcion1.text}", "${edtopcion2.text}","${edtOpcion3.text}","${edtRespuesta.text}","${edtarea.text}","${edtDescripcion.text}")

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