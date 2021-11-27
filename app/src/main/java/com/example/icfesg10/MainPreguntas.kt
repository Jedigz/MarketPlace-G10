package com.example.icfesg10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import androidx.lifecycle.Observer
import com.example.icfesg10.database.SaberProDB
import com.example.icfesg10.databinding.ActivityMainPreguntasBinding
import com.example.icfesg10.model.Pregunta
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main_preguntas.*

class MainPreguntas() : AppCompatActivity(), Parcelable {
    private lateinit var binding: ActivityMainPreguntasBinding
    private lateinit var auth: FirebaseAuth

    constructor(parcel: Parcel) : this() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main_preguntas)
        binding = ActivityMainPreguntasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.btnCerrarsesion.setOnClickListener {
            cerrarsesion()
        }

        var listaPreguntas = emptyList<Pregunta>()
        val database = SaberProDB.getDatabase(this)

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
                .replace(
                    R.id.fragment_container_view,
                    DetallePreguntaFragment::class.java,
                    pregunta,
                    "detallePregunta"
                )
                .commit()

        }

    }

    private fun cerrarsesion() {
        auth.signOut()
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
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
