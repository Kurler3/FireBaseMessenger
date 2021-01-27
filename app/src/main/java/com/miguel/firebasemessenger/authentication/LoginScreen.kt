package com.miguel.firebasemessenger.authentication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.miguel.firebasemessenger.R
import kotlinx.android.synthetic.main.fragment_login_screen.*

class LoginScreen : Fragment(R.layout.fragment_login_screen) {
    private val TAG = LoginScreen::class.java.name

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        login_btn.setOnClickListener {
            val email = login_email_edit_text.text.toString()
            val password = login_password_edit_text.text.toString()

            onLoginBtnClick(email, password)
        }

        no_have_account_text.setOnClickListener {
            val action = LoginScreenDirections.actionLoginScreenToRegisterScreen()
            findNavController().navigate(action)
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser ?: null
        if(currentUser!=null){
            //Launch him into the App
        }
    }

    fun onLoginBtnClick(email: String, password: String) {
        if (CheckInput(email, password)) {
            // Handle authentication in firebase
            Log.d("RegisterViewModel", "email: $email")
            Log.d("RegisterViewModel", "password: $password")

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        Log.d(TAG, "User signed in successfully")
                        val user = auth.currentUser
                        launchHomeScreen(user)
                    }else{
                        Log.d(TAG, "User not signed in successfully")

                        view?.let { v ->
                            val snackbar = Snackbar.make(v, "Email or/and password invalid. Please try again", Snackbar.LENGTH_LONG)
                            snackbar.setAction("Ok"){
                                snackbar.dismiss()
                            }
                            snackbar.show()
                        }
                    }
                }
        }else{
            view?.let {
                val snackbar = Snackbar.make(it, "Input invalid. Try again", Snackbar.LENGTH_LONG)
                snackbar.setAction("Ok"){
                    snackbar.dismiss()
                }
                snackbar.show()
            }
        }
    }
    private fun CheckInput(email: String, password: String): Boolean {
        if (email.isNotBlank() && password.isNotBlank()) {
            return true
        }
        return false
    }
    private fun launchHomeScreen(user: FirebaseUser?){
        //Get user info and launch to new frag
    }
}