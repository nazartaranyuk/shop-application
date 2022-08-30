package com.nazartaraniuk.shopapplication.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.nazartaraniuk.shopapplication.MainActivity
import com.nazartaraniuk.shopapplication.R
import com.nazartaraniuk.shopapplication.databinding.ActivitySigninBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController

@SuppressLint("CustomSplashScreen")
class SignInActivity : AppCompatActivity() {

    private var binding: ActivitySigninBinding? = null
    private lateinit var signIn: ActivityResultLauncher<Intent>
    private lateinit var auth: FirebaseAuth

    private lateinit var googleSignInOptions: GoogleSignInOptions
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        auth = Firebase.auth
        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)


        signIn = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    firebaseAuthWithGoogle(account.idToken!!)
                } else {
                    Toast.makeText(this, account.toString(), Toast.LENGTH_LONG)
                }
            } catch (e: ApiException) {
                Log.d("SignInActivity", "Something went wrong")
            }
        }

        binding?.btnSignUp?.setOnClickListener {
            signInWithGoogle()
        }

        setContentView(binding?.root)
    }

    override fun onStart() {
        super.onStart()
//        if (Firebase.auth.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
//        }
    }

    // launch google accounts intent
    private fun signInWithGoogle() {
        signIn.launch(googleSignInClient.signInIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Remove video playing
        binding?.videoView?.release()
    }

    private fun firebaseAuthWithGoogle(id: String) {
        val credential = GoogleAuthProvider.getCredential(id, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Log.d("SignInActivity", "Sign in error")
            }
        }
    }
}