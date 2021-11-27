package com.example.icfesg10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.EditText
import android.widget.Toast
import com.example.icfesg10.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        auth = Firebase.auth

        binding.btningresar.setOnClickListener {
            val edtEmail = findViewById<EditText>(R.id.edtEmail)
            val edtPassword = findViewById<EditText>(R.id.edtpassword)
            var validate = true

            if (edtEmail.text.toString().trim() == ""
                || !android.util.Patterns.EMAIL_ADDRESS.matcher(edtEmail.text).matches()
            ) {
                edtEmail.error = "El e-mail es invalido o es vacío"
                validate = false
            }

            if (edtPassword.text.toString().trim() == "") {
                edtPassword.error = "La contraseña no puede ser vacía"
                validate = false
            }

            if (validate) {
                onLogin(edtEmail.text.toString(), edtPassword.text.toString())
            }
        }
    }


    fun Registrese() {
        val intento = Intent(this, Signup::class.java)
        startActivity(intento)
    }

    fun onLogin(email: String, pass: String) {
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    startActivity(Intent(this, MainPreguntas::class.java))
                } else {
                    clearFormLogin()
                    Toast.makeText(
                        this,
                        "El usuario o contraseña no se reconocen",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun clearFormLogin() {
        val edtEmail = findViewById<EditText>(R.id.edtEmail)
        val edtPassword = findViewById<EditText>(R.id.edtpassword)

        edtEmail.setText("")
        edtEmail.requestFocus()
        edtPassword.setText("")
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, MainPreguntas::class.java))
        } else {
            //Registrese()
        }
    }
}