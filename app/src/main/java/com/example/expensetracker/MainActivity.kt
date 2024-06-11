package com.example.expensetracker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.expensetracker.databinding.ActivityMainBinding
import com.example.expensetracker.fragment.AnalysisFragment
import com.example.expensetracker.fragment.HomeFragment
import com.example.expensetracker.fragment.ProfileFragment
import com.example.expensetracker.fragment.TransactionFragment

class MainActivity : AppCompatActivity() , BottomnavListener {
    private lateinit var binding: ActivityMainBinding
    private var backPressedOnce = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.itemIconTintList = null
        val frag = intent.getStringExtra("frag")
        Log.d("FRAGMENT IS " , frag.toString())
        if (frag == "2") {
            replaceFragment(TransactionFragment())
        }
        replaceFragment(HomeFragment())
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    replaceFragment(HomeFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.analysis -> {
                    replaceFragment(AnalysisFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.profile -> {
                    replaceFragment(ProfileFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.transaction -> {
                    replaceFragment(TransactionFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                else -> {
                    return@setOnNavigationItemSelectedListener false
                }
            }
        }
        binding.fab.setOnClickListener {
            val i = Intent(this@MainActivity , AddData::class.java)
            startActivity(i)
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val homeFragment = HomeFragment()
        val currentFragment = supportFragmentManager.findFragmentById(R.id.frameLayout)
        if (currentFragment !is HomeFragment) {
            replaceFragment(homeFragment)
            binding.bottomNavigationView.selectedItemId = R.id.home
        } else {
            if (backPressedOnce) {
                super.onBackPressed()
                return
            }
            this.backPressedOnce = true
            Toast.makeText(this , "Press again to exit" , Toast.LENGTH_SHORT).show()
            android.os.Handler().postDelayed({ backPressedOnce = false } , 2000)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction =
            fragmentManager.beginTransaction()
        fragmentManager.popBackStack()
        transaction.replace(R.id.frameLayout , fragment)
        transaction.commit()
    }

    override fun changeBottomNavListener(id: Int) {
        binding.bottomNavigationView.selectedItemId = id
    }
}
