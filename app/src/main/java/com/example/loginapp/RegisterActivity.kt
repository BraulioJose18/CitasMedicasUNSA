package com.example.loginapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class RegisterActivity : AppCompatActivity() {

    private lateinit var  name:EditText
    private lateinit var  firstlastname:EditText
    private lateinit var  secondlastname:EditText
    private lateinit var  dni:EditText
    private lateinit var  correo:EditText
    private lateinit var  password:EditText
    //private lateinit var  progressBar:ProgressBar
    private lateinit var  dbReference: DatabaseReference
    private lateinit var  database:FirebaseDatabase
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        name=findViewById(R.id.name)
        firstlastname=findViewById(R.id.firstlastname)
        secondlastname=findViewById(R.id.secondlastname)
        dni=findViewById(R.id.dni)
        correo=findViewById(R.id.correo)
        password=findViewById(R.id.password)

       // progressBar=ProgressBar( context:this)

        database= FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()

        dbReference=database.reference.child("User")
    }
    fun register(view:View){
        createNewAccount()
    }
    private fun createNewAccount(){
        val name:String=name.text.toString()
        val firstlastname:String=firstlastname.text.toString()
        val secondlastname:String=secondlastname.text.toString()
        val dni:String=dni.text.toString()
        val correo:String=correo.text.toString()
        val password:String=password.text.toString()
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(firstlastname) && !TextUtils.isEmpty(secondlastname) &&
            !TextUtils.isEmpty(dni) && !TextUtils.isEmpty(correo) && !TextUtils.isEmpty(password) ){
            auth.createUserWithEmailAndPassword(correo,password).addOnCompleteListener(this){
                    task ->
                    if (task.isComplete){
                        val user:FirebaseUser?=auth.currentUser
                        verifyEmail(user)
                        val userBD = user?.uid?.let { dbReference.child(it) }
                        userBD?.child("Name")?.setValue(name)
                        userBD?.child("FirstLastName")?.setValue(firstlastname)
                        userBD?.child("FirstLastName")?.setValue(firstlastname)
                        userBD?.child("SecondLastName")?.setValue(secondlastname)
                        userBD?.child("dni")?.setValue(dni)
                        action()
                    }
            }
        }
    }

    private fun action(){
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun verifyEmail(user:FirebaseUser?){
        user?.sendEmailVerification()?.addOnCompleteListener(this){ task ->
            if(task.isComplete){
                Toast.makeText(this,"Correo enviado",
                    Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this,"Error al enviar el correo",
                    Toast.LENGTH_LONG).show()
            }
        }
    }
}
