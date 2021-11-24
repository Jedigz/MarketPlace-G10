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
import com.example.marketplaceg10.dao.SaberProDAO
import com.example.marketplaceg10.database.saberProDB
import com.example.marketplaceg10.model.Pregunta
import kotlinx.android.synthetic.main.fragment_detalle_pregunta.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetallePreguntaFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val fragmento = inflater.inflate(R.layout.fragment_detalle_pregunta, container, false)

        //contexto de la aplicacion}
        val context = activity?.applicationContext

        val idPregunta =  requireArguments().getInt("idPregunta")

        verPregunta( fragmento, idPregunta)

        fragmento.findViewById<ImageButton>( R.id.btnCancelar).setOnClickListener {
            salir()
        }

        fragmento.findViewById<CheckBox>( R.id.chkEditar).setOnClickListener {
            activarActualizar(fragmento, fragmento.findViewById<CheckBox>( R.id.chkEditar).isChecked )
        }

        fragmento.findViewById<ImageButton>( R.id.btnActualizar).setOnClickListener {
            actualizarPregunta(fragmento, idPregunta)
        }

        fragmento.findViewById<ImageButton>( R.id.btnEliminar).setOnClickListener {
            eliminarPregunta(idPregunta)
        }

        return fragmento
    }
    private fun eliminarPregunta(idPregunta: Int) {
        CoroutineScope( Dispatchers.IO ).launch {
            val database = context?.let { saberProDB.getDatabase(it) }
            val pregunta = Pregunta(idPregunta, "", "", "","","","")
            database?.SaberProDAO()?.deletePregunta(pregunta)
        }
        salir()
    }

    private fun actualizarPregunta(fragmento: View, idpregunta: Int) {
        CoroutineScope( Dispatchers.IO).launch {
            val database = context?.let { saberProDB.getDatabase(it)}

            val pregunta = Pregunta(
                idpregunta,
                fragmento.findViewById<EditText>(R.id.edtexto).text.toString(),
                fragmento.findViewById<EditText>(R.id.edtOpcion1).text.toString(),
                fragmento.findViewById<EditText>(R.id.edtOpcion2).text.toString(),
                fragmento.findViewById<EditText>(R.id.edtOpcion3).text.toString(),
                fragmento.findViewById<EditText>(R.id.edtRespuesta).text.toString(),
                fragmento.findViewById<EditText>(R.id.edtarea).text.toString()
           )
            database?.SaberProDAO()?.updatePregunta(pregunta)
        }
        activarActualizar(fragmento, false)
        fragmento.findViewById<CheckBox>( R.id.chkEditar).isChecked = false
        fragmento.findViewById<ImageButton>(R.id.btnActualizar).visibility = View.GONE
    }

    private fun verPregunta( fragmento: View, idPregunta: Int) {
        var pregunta = Pregunta(idPregunta, "", "", "","","","")

        CoroutineScope( Dispatchers.IO ).launch {
            //obtener la instancia de la BDs
            val database = context?.let { saberProDB.getDatabase(it) }

            pregunta = database?.SaberProDAO()?.getPreguntaPorId(idPregunta)!!

            val edtexto = fragmento.findViewById<EditText>( R.id.edtexto)
            val edtOpcion1 = fragmento.findViewById<EditText>( R.id.edtOpcion1)
            val edtOpcion2 = fragmento.findViewById<EditText>( R.id.edtOpcion2)
            val edtOpcion3 = fragmento.findViewById<EditText>( R.id.edtOpcion3)
            val edtRespuesta = fragmento.findViewById<EditText>( R.id.edtRespuesta)
            val edtArea = fragmento.findViewById<EditText>( R.id.edtarea)
            edtexto.setText( pregunta.PreTexto )
            edtOpcion1.setText( pregunta.Opcion1)
            edtOpcion2.setText( pregunta.Opcion2)
            edtOpcion3.setText( pregunta.Opcion3)
            edtRespuesta.setText( pregunta.Respuesta)
            edtArea.setText(pregunta.Area)

        }
    }

    private fun activarActualizar(fragmento: View, activo: Boolean ){
        fragmento.findViewById<EditText>( R.id.edtexto).setEnabled( activo )
        fragmento.findViewById<EditText>( R.id.edtOpcion1).setEnabled( activo )
        fragmento.findViewById<EditText>( R.id.edtOpcion2).setEnabled( activo )
        fragmento.findViewById<EditText>( R.id.edtOpcion3).setEnabled( activo )
        fragmento.findViewById<EditText>( R.id.edtRespuesta).setEnabled( activo )
        fragmento.findViewById<EditText>( R.id.edtarea).setEnabled( activo )

        fragmento.findViewById<ImageButton>(R.id.btnActualizar).visibility = View.VISIBLE
    }

    private fun salir(){
        val lvPreguntas= activity?.findViewById<ListView>( R.id.lvpreguntas )
        lvPreguntas?.visibility = View.VISIBLE

        activity?.supportFragmentManager?.beginTransaction()
            ?.remove( this )
            ?.commit()
    }
}