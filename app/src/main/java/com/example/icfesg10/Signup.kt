package com.example.icfesg10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast

class Signup() : AppCompatActivity(), Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Signup> {
        override fun createFromParcel(parcel: Parcel): Signup {
            return Signup(parcel)
        }

        override fun newArray(size: Int): Array<Signup?> {
            return arrayOfNulls(size)
        }
    }

    fun fnCrearcuenta(view: View) {
        val checkBox: CheckBox = findViewById<View>(R.id.cbTerminos) as CheckBox
        val etNombres = findViewById<EditText>(R.id.etNombres)
        val etApellidos = findViewById<EditText>(R.id.etApellidos)
        val etCorreo = findViewById<EditText>(R.id.etCorreo)
        val etConfirmaCorreo = findViewById<EditText>(R.id.etConfirmaCorreo)
        var correcto: Boolean = false

        if (etNombres.text.toString().trim() == "") {
            etNombres.error = "Campo no puede ser vacio"
        }
        if (etApellidos.text.toString().trim() == "") {
            etApellidos.error = "Campo no puede ser vacio"
        }
        if (etCorreo.text.toString().trim() == "") {
            etCorreo.error = "Campo no puede ser vacio"
        }
        if (etConfirmaCorreo.text.toString().trim() == "") {
            etConfirmaCorreo.error = "Campo no puede ser vacio"
        }
        if (!checkBox.isChecked)
            Toast.makeText(this, "Debe Seleccionar terminos y condiciones", Toast.LENGTH_LONG)
                .show()
        else
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etCorreo.text).matches())
                etCorreo.error = "El formato del Mail es incorrecto"
        if (etCorreo.text.toString() != etConfirmaCorreo.text.toString())
            Toast.makeText(
                this,
                "El correo y la confirmación de correo deben ser iguales",
                Toast.LENGTH_LONG
            ).show()
        else
            Toast.makeText(this, "Usuario Creado", Toast.LENGTH_LONG).show()
    }
}