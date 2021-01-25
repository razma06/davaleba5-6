package com.example.daveleba5_6

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_log_in.*

class LogInActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        init()

    }


    private fun init() {
        auth = Firebase.auth
        logInButton.setOnClickListener() {
            logIn()
        }
    }

    private fun logIn() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            progressBar.visibility = View.VISIBLE
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    progressBar.visibility = View.GONE
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        d("logIn", "signInWithEmail:success")
                        val user = auth.currentUser
                        val intent = Intent(this, DashboardActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    } else {
                        // If sign in fails, display a message to the user.
                        d("logIn", "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed." + task.exception,
                            Toast.LENGTH_SHORT).show()
                    }

                }
        } else {
            Toast.makeText(this, "Please Fill All Fields", Toast.LENGTH_SHORT).show()
        }


    }
}
