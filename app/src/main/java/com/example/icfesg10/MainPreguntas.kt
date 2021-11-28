package com.example.icfesg10

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.icfesg10.databinding.ActivityMainPreguntasBinding
import com.example.icfesg10.model.Pregunta
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize

class MainPreguntas() : AppCompatActivity() {
    private lateinit var binding: ActivityMainPreguntasBinding
    private lateinit var auth: FirebaseAuth

    private lateinit var listaPreguntas: ArrayList<Pregunta>
    private lateinit var PreguntasAdapter: ArrayAdapter<Pregunta>

    var database =Firebase.database
    var dbReferenciaPreguntas= database.getReference("preguntas")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainPreguntasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        Firebase.initialize(this)

        listaPreguntas = ArrayList<Pregunta>()

        binding.btnAdicionaPregunta.setOnClickListener {
            val intent = Intent(this, AdicionarPreguntas::class.java)
            this.startActivity(intent)
        }

        verListadoPreguntas()

        binding.lvPreguntas.setOnItemClickListener { parent, view, position, id ->
            var pregunta = listaPreguntas[position]

            val intent = Intent(this, EditarPreguntas::class.java)
            intent.putExtra("pregunta", pregunta)
            this.startActivity(intent)
        }
    }
    private fun verListadoPreguntas() {

        val preguntaItemListener = object : ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                for (pel in datasnapshot.children) {

                    // Objeto MAP
                    val mapPregunta: Map<String, Any> = pel.value as HashMap<String, Any>

                    var pregunta: Pregunta = Pregunta(
                        mapPregunta.get("id").toString(),
                        mapPregunta.get("preTexto").toString(),
                        mapPregunta.get("opcion1").toString(),
                        mapPregunta.get("opcion2").toString(),
                        mapPregunta.get("opcion3").toString(),
                        mapPregunta.get("respuesta").toString(),
                        mapPregunta.get("area").toString(),
                        mapPregunta.get("descripcion").toString()
                    )
                    listaPreguntas.add(pregunta)
                    PreguntasAdapter = PreguntasAdapter(this@MainPreguntas, listaPreguntas)
                    binding.lvPreguntas.adapter = PreguntasAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        dbReferenciaPreguntas.addValueEventListener(preguntaItemListener)
    }

    private fun cerrarSesion() {
        auth.signOut()
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
    }
 }




