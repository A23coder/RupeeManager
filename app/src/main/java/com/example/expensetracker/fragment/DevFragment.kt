package com.example.expensetracker.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.expensetracker.databinding.FragmentDevBinding


class DevFragment : Fragment() {
    private lateinit var binding: FragmentDevBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("CommitTransaction")
    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? , savedInstanceState: Bundle?
    ): View {
        binding = FragmentDevBinding.inflate(inflater , container , false)

        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
    }

}