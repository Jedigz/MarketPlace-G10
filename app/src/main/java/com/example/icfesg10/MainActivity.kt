package com.example.icfesg10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText


class MainActivity : AppCompatActivity() {
    private var edtUsername: EditText? = null
    private var edtPassword: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtUsername = findViewById(R.id.edtUserName)
        edtPassword = findViewById(R.id.edtpassword)

    }

    fun onRegistrese(view: View) {
        val intento = Intent(this, Signup::class.java)
        startActivity(intento)
    }

    fun onLogin(view: View) {
        /*if (edtUsername!!.text.toString()=="123@abc.com")
            if(edtPassword!!.text.toString()=="1234"){*/
        startActivity(Intent(this, MainPreguntas::class.java))

        //}

    }

}