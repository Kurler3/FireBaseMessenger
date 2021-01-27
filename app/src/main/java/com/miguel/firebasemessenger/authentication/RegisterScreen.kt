package com.miguel.firebasemessenger.authentication

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.miguel.firebasemessenger.R
import kotlinx.android.synthetic.main.register_screen.*
import kotlinx.coroutines.launch


class RegisterScreen : Fragment(R.layout.register_screen) {
    private val TAG = RegisterScreen::class.java.name

    private lateinit var username : String
    private lateinit var email : String
    private lateinit var password: String

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        register_select_image.setOnClickListener {
            // Launch something that allows user to select an image
        }

        register_btn.setOnClickListener {
            username = register_username_edit_text.text.toString()
            email = register_email_edit_text.text.toString()
            password = register_password_edit_text.text.toString()

            onRegisterBtnClick(username, email, password)
        }


        already_have_account_text.setOnClickListener {
            val action = RegisterScreenDirections.actionRegisterScreenToLoginScreen()
            findNavController().navigate(action)
        }
    }


    private fun onRegisterBtnClick(username: String, email: String, password: String){
        if(CheckInput(username, email, password)){
            // Handle authentication in firebase
            Log.d("RegisterViewModel", "username: $username")
            Log.d("RegisterViewModel", "email: $email")
            Log.d("RegisterViewModel", "password: $password")

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        Log.d(TAG, "Successfully Created account")
                        val user = auth.currentUser
                        LaunchMainScreen(user)
                    }else{
                        Log.d(TAG, "Account not created")
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
    private fun CheckInput(username: String, email: String, password: String) : Boolean {
        if(username.isNotBlank() && email.isNotBlank() && password.isNotBlank()){
            return true
        }
        return false
    }
    private fun LaunchMainScreen(user: FirebaseUser?){
        if(user!=null){
            //Get the details and send to next fragment
        }
    }



}