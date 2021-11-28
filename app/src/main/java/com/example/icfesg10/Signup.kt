package com.example.icfesg10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import com.example.icfesg10.databinding.ActivitySignupBinding
import com.example.icfesg10.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize

class Signup() : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth
    val database = Firebase.database
    val dbUsersReference = database.getReference("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Firebase.initialize(this)
        auth = Firebase.auth

        binding.btnRegistrarUsuario.setOnClickListener {
            registrarUsuario()
        }
    }

    fun registrarUsuario() {
        val edtNombres = findViewById<EditText>(R.id.etNombres)
        val edtApellidos = findViewById<EditText>(R.id.etApellidos)
        val edtUsuario = findViewById<EditText>(R.id.etUsuario)
        val edtCorreo = findViewById<EditText>(R.id.etCorreo)
        val edtConfirmaCorreo = findViewById<EditText>(R.id.etConfirmaCorreo)
        val edtContrasena = findViewById<EditText>(R.id.etContrasena)
        val rbSoyProfesor = findViewById<RadioButton>(R.id.radio_soy_profesor)
        val rbSoyEstudiante = findViewById<RadioButton>(R.id.radio_soy_estudiante)
        val cbTerminos = findViewById<CheckBox>(R.id.cbTerminos)
        var validate: Boolean = true


        if (edtNombres.text.toString().trim() == "") {
            edtNombres.error = "El nombre no puede ser vacio"
            validate = false
        }

        if (edtApellidos.text.toString().trim() == "") {
            edtApellidos.error = "Los apellidos no pueden ser vacios"
            validate = false
        }

        if (edtUsuario.text.toString().trim() == "") {
            edtUsuario.error = "El nombre de usuario no puede ser vacio"
            validate = false
        }

        if (edtCorreo.text.toString().trim() == ""
            || !android.util.Patterns.EMAIL_ADDRESS.matcher(edtCorreo.text).matches()
        ) {
            edtCorreo.error = "El correo es invalido o es vacío"
            validate = false
        }

        if (edtConfirmaCorreo.text.toString().trim() == ""
            || !android.util.Patterns.EMAIL_ADDRESS.matcher(edtConfirmaCorreo.text).matches()
            || edtCorreo.text.toString() != edtConfirmaCorreo.text.toString()
        ) {
            edtConfirmaCorreo.error = "La confirmación del correo es invalida o es vacía"
            validate = false
        }

        if (edtContrasena.text.toString().trim() == "" || edtContrasena.text.length <= 6) {
            edtContrasena.error = "La contraseña no puede ser vacía, logitud minima 6 caracteres"
            validate = false
        }

        if (!rbSoyProfesor.isChecked && !rbSoyEstudiante.isChecked) {
            Toast.makeText(
                this,
                "Seleccione que tipo de usuario es",
                Toast.LENGTH_LONG
            ).show()
            validate = false
        }

        if (!cbTerminos.isChecked) {
            cbTerminos.error = "Debe marcar la casilla de terminos y condiciones"
            validate = false
        }

        if (validate) {
            auth.createUserWithEmailAndPassword(
                edtCorreo.text.toString(),
                edtContrasena.text.toString()
            ).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val currentUser = auth.currentUser

                    if (currentUser != null) {
                        var user = User(
                            currentUser.uid,
                            edtNombres.text.toString(),
                            edtApellidos.text.toString(),
                            edtUsuario.text.toString(),
                            currentUser.email.toString(),
                            if (rbSoyProfesor.isChecked) 1 else 2
                        )

                        crearUsuario(user)

                        startActivity(Intent(this, MainActivity::class.java))
                        Toast.makeText(
                            this,
                            "El usuario se registro correctamente",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this,
                        "Ocurrio un error al realizar el registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    fun crearUsuario(user: User) {
        dbUsersReference.child(user.uid).setValue(user)
    }
}