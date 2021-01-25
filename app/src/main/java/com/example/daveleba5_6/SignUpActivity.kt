package com.example.daveleba5_6

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_authentication.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.signUpButton

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        init()
    }

    private fun init() {
        auth = FirebaseAuth.getInstance()
        signUpButton.setOnClickListener {
            if (emailEditText.text.toString().isNotEmpty() || passwordEditText.text.toString().isNotEmpty()){
                Toast.makeText(this,"SignUp is Success!",Toast.LENGTH_LONG).show()

            }else{
                Toast.makeText(this,"Email format is not Correct",Toast.LENGTH_LONG).show()
            }
            signUp()

        }
    }

    private fun signUp() {
        val email : String = emailEditText.text.toString()
        val password : String = passwordEditText.text.toString()
        val confirmPassword : String = confirmEditText.text.toString()


        if (email.isNotEmpty() &&
            password.isNotEmpty() && confirmPassword.isNotEmpty()
        ) {
            if (password == confirmPassword) {
                progressBar.visibility = View.VISIBLE
                deleteClick(isStarted = true)
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        progressBar.visibility = View.GONE
                        deleteClick(isStarted = false)
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            d("signUp", "createUserWithEmail:success")
                            val user = auth.currentUser

                        } else {
                            // If sign in fails, display a message to the user.
                            d("signUp", "createUserWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }


    }
    private fun deleteClick(isStarted:Boolean){
        signUpButton.isClickable=! isStarted
        if (isStarted)
            progressBar.visibility=View.VISIBLE
        else
            progressBar.visibility=View.GONE
    }


}