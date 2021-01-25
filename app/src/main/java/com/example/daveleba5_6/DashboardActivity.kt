package com.example.daveleba5_6

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.daveleba5_6.Constants
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.daveleba5_6.Constants.DASHBOARD_ACTIVITY_TAG
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.rpc.context.AttributeContext
import kotlinx.android.synthetic.main.activity_dashboard.*



data class User(
    val displayName: String = "",
    val state: String = ""
)


class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


class DashboardActivity : AppCompatActivity() {
    private lateinit var rvUsers: RecyclerView
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        auth = Firebase.auth
        rvUsers = findViewById(R.id.rcView)
        db = Firebase.firestore

        val query = db.collection("users")

        val options = FirestoreRecyclerOptions.Builder<User>().setQuery(query, User::class.java)
                .setLifecycleOwner(this).build()

        val adapter = object: FirestoreRecyclerAdapter<User, UserViewHolder>(options){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
                val view = LayoutInflater.from(this@DashboardActivity).inflate(android.R.layout.simple_list_item_2, parent, false)
                return UserViewHolder(view)
            }

            override fun onBindViewHolder(holder: UserViewHolder, position: Int, model: User) {
               val tvName: TextView = holder.itemView.findViewById(android.R.id.text1)
               val stateText: TextView = holder.itemView.findViewById(android.R.id.text2)

               tvName.text = model.displayName
               stateText.text = model.state


               stateText.setOnClickListener{
                   Log.i(Constants.DASHBOARD_ACTIVITY_TAG, "CLICKED")
               }
            }

        }





        rvUsers.adapter = adapter
        rvUsers.layoutManager = LinearLayoutManager(this,)


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menuLogOut){
            Log.d(Constants.DASHBOARD_ACTIVITY_TAG,"Log Out")


            val logOutIntent = Intent(this, LogInActivity::class.java)
            logOutIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(logOutIntent)
            finish()


        }
        else if(item.itemId == R.id.mEditState)
            Log.i(Constants.DASHBOARD_ACTIVITY_TAG, "Edit State")
            showActionDialog()

        return super.onOptionsItemSelected(item)
    }

    private fun showActionDialog() {
        val editText = EditText(this)


        val dialog = AlertDialog.Builder(this,)
            .setTitle("Update your state")
            .setView(editText)
            .setNegativeButton("cancel", null)
            .setPositiveButton("OK", null)
            .show()

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener{
            Log.i(Constants.DASHBOARD_ACTIVITY_TAG, "Clicked on positive Button!") }

        val stateText = editText.text.toString()
        if(stateText.isBlank()) {
            Toast.makeText(this, "Cannot Sumbit empty Text", Toast.LENGTH_LONG).show()

        }

        val currentUser = auth.currentUser
        if(currentUser == null) {
            Toast.makeText(this, "No Signed In user", Toast.LENGTH_LONG).show()

        }
        val data =  User("January", "Capricorn until 21th")

//        db.collection("users")
//            .add(data)
//            .addOnSuccessListener { documentReference ->
//                Log.d(Constants.DASHBOARD_ACTIVITY_TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
//            }
//            .addOnFailureListener { e ->
//                Log.w(Constants.DASHBOARD_ACTIVITY_TAG, "Error adding document", e)
//            }

    }



}






