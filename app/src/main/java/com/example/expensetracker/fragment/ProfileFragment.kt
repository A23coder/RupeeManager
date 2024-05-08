package com.example.expensetracker.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.expensetracker.AddData
import com.example.expensetracker.BottomnavListener
import com.example.expensetracker.MainActivity
import com.example.expensetracker.R
import com.example.expensetracker.databinding.FragmentProfileBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN: Int = 123
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    private lateinit var binding: FragmentProfileBinding
    private var bottomnavListener: BottomnavListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BottomnavListener) {
            bottomnavListener = context
        } else {
            throw RuntimeException("Context not implemented")
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? , savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater , container , false)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_client_id)).requestEmail().build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireContext().applicationContext , gso)

        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            binding.tvUsername.text = currentUser.displayName.toString()
            binding.tvEmail.text = currentUser.email.toString()
            Glide.with(this).load(currentUser.photoUrl).into(binding.imgPr)
            println("=====USer ${currentUser.uid} ${currentUser.email}")
        }

        binding.imgLogout?.setOnClickListener {
            Firebase.auth.signOut()
            binding.imgPr.setImageResource(R.drawable.user)
            binding.tvUsername.text = resources.getString(R.string.username_)
            binding.tvEmail.text = "email:Address"
            Toast.makeText(
                context?.applicationContext , "Successfully Log Out" , Toast.LENGTH_SHORT
            ).show()
        }

        binding.fabCloudUpload.setOnClickListener {
            googleSignIn()
        }
        return binding.root
    }

    private fun googleSignIn() {
        val intent = mGoogleSignInClient.signInIntent
        startActivityForResult(intent , RC_SIGN_IN)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int , resultCode: Int , data: Intent?) {
        super.onActivityResult(requestCode , resultCode , data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    firebaseAuthWithGoogle(account.idToken!!)
                }
            } catch (e: ApiException) {
                Toast.makeText(context , "Google sign in failed: ${e.message}" , Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken , null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    val map: HashMap<String , String> = HashMap()
                    map["id"] = user!!.uid
                    map["name"] = user.displayName.toString()
                    map["profile"] = user.photoUrl.toString()
                    database.reference.child("users").child(user.uid).setValue(map)
                    Toast.makeText(context , "Sign in successful!" , Toast.LENGTH_SHORT).show()

                    bottomnavListener?.changeBottomNavListener(R.id.home)
                    (activity as MainActivity).supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout , HomeFragment())
                        .addToBackStack(this@ProfileFragment.toString()).commit()

                } else {
                    Toast.makeText(
                        context ,
                        "Authentication failed: ${task.exception?.message}" ,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    @SuppressLint("CommitTransaction")
    override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
        super.onViewCreated(view , savedInstanceState)

        binding.imgTransaction.setOnClickListener {
            bottomnavListener?.changeBottomNavListener(R.id.transaction)
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout , TransactionFragment())
                .addToBackStack(this@ProfileFragment.toString()).setReorderingAllowed(true).commit()
            return@setOnClickListener
        }

        binding.imgAnalisys.setOnClickListener {
            bottomnavListener?.changeBottomNavListener(R.id.analysis)
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout , AnalysisFragment())
                .addToBackStack(this@ProfileFragment.toString()).commit()
            return@setOnClickListener
        }
        binding.imgAboutApp.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout , AboutFragment())
                .addToBackStack(this@ProfileFragment.toString()).commit()
            return@setOnClickListener
        }

        binding.imgDeveloper.setOnClickListener {
            Toast.makeText(context , "Updating very soon...." , Toast.LENGTH_SHORT).show()
        }

        binding.imgAddData.setOnClickListener {
            startActivity(Intent(context , AddData::class.java))
        }
        binding.imgExportData.setOnClickListener {
            Toast.makeText(context , "Updating very soon...." , Toast.LENGTH_LONG).show()
        }
        binding.imgContactUs.setOnClickListener {
            Toast.makeText(context , "Updating very soon...." , Toast.LENGTH_LONG).show()
        }
    }


}
