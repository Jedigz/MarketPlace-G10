package com.example.icfesg10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.icfesg10.database.saberProDB
import com.example.icfesg10.model.Pregunta
import kotlinx.android.synthetic.main.activity_main_preguntas.*

class MainPreguntas() : AppCompatActivity(),Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_preguntas)

        var listaPreguntas = emptyList<Pregunta>()
        val database = saberProDB.getDatabase(this)

        database.SaberProDAO().getAllPreguntas().observe(this, Observer {
            listaPreguntas = it
            val adapter = PreguntasAdapter(this, listaPreguntas)
            lvPreguntas.adapter = adapter
        })


        btnAdicionaPregunta.setOnClickListener {
            //Se oculta el listado de Peliculas para mostrar la ventana de adicionar Pelicula
            lvPreguntas.visibility = View.GONE

            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(
                    R.id.fragment_container_view,
                    PreguntasFragment::class.java,
                    null,
                    "Preguntas"
                )
                .commit()
        }
        lvPreguntas.setOnItemClickListener { parent, view, position, id ->
            val pregunta = Bundle()
            pregunta.putInt("idPregunta", listaPreguntas[position].id)

            lvPreguntas.visibility = View.GONE

            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container_view,
                    DetallePreguntaFragment::class.java,
                    pregunta,
                    "detallePregunta"
                )
                .commit()

        }

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MainPreguntas> {
        override fun createFromParcel(parcel: Parcel): MainPreguntas {
            return MainPreguntas(parcel)
        }

        override fun newArray(size: Int): Array<MainPreguntas?> {
            return arrayOfNulls(size)
        }
    }

}
