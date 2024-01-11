package com.example.growwtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.growwtest.databinding.ActivityMainBinding
import com.example.growwtest.ui.characterlisting.CharacterListingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val frag = CharacterListingFragment()
        setCurrentFragment(frag)
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.flMainActivity, fragment)
            .commit()
    }

    fun addCurrentFragToBackStack(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.flMainActivity, fragment)
            .addToBackStack("fraga")
            .commit()
    }
}