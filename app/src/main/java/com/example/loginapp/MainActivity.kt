package com.example.loginapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var email:EditText
    private lateinit var password:EditText
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email=findViewById(R.id.email)
        password=findViewById(R.id.password)
        auth=FirebaseAuth.getInstance()

    }
    fun register(view:View){
        startActivity(Intent(this,RegisterActivity::class.java))
    }
    fun login(view:View){
        loginUser()
    }
    private fun loginUser(){
        val email:String=email.text.toString()
        val password:String=password.text.toString()
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this){
                    task ->

                    if(task.isSuccessful){
                        action()
                    } else {
                        Toast.makeText(this,"Error en Inicio de Sesión",Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
    private fun action(){
        startActivity(Intent(this,menu_principal::class.java))
    }
}
