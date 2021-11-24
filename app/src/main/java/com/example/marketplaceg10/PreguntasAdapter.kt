package com.example.marketplaceg10

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.marketplaceg10.model.Pregunta
import kotlinx.android.synthetic.main.preguntas_item.view.*

class PreguntasAdapter (private val mContext: Context, val listaPreguntas:List<Pregunta>)
    : ArrayAdapter<Pregunta>(mContext,0,listaPreguntas){
    override fun getView (posicion: Int, view: View?, viewGroup: ViewGroup): View {
        val layout = LayoutInflater.from(mContext).inflate(R.layout.preguntas_item,viewGroup,false)
        val pregunta = listaPreguntas[posicion]
        layout.tvTexto.text=pregunta.PreTexto
        layout.tvOpcion1.text=pregunta.Opcion1
        layout.tvOpcion2.text=pregunta.Opcion2
        layout.tvOpcion3.text=pregunta.Opcion3
        layout.tvRespuesta.text=pregunta.Respuesta
        layout.tvArea.text=pregunta.Area
        return layout
    }
}
