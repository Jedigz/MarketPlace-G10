package com.example.marketplaceg10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.marketplaceg10.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth
    private lateinit var binding : ActivityMainBinding


    private var edtUsername: EditText?=null
    private var edtPassword: EditText?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.btningresar.setOnClickListener{

        onLogin(binding.edtUserName.text.toString(),binding.edtpassword.text.toString())

        }

        edtUsername=findViewById(R.id.edtUserName)
        edtPassword=findViewById(R.id.edtpassword)

    }



    fun Registrese() {
        val intento=Intent(this,signup::class.java)
        startActivity(intento)
    }

    fun onLogin(email:String,pass:String) {
        auth.signInWithEmailAndPassword(email,pass)
            .addOnCompleteListener(this){ task ->
                if (task.isSuccessful){
                    val user=auth.currentUser
                    startActivity(Intent(this,MainPreguntas::class.java))
                } else{
                   // Registrese()
                }
            }
    }

    public override fun onStart(){
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null){
            startActivity(Intent(this,MainPreguntas::class.java))
        }else {
            //Registrese()
        }

    }

}