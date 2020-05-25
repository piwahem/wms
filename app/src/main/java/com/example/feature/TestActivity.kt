package com.example.feature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class TestActivity : AppCompatActivity() {

    private lateinit var bottomNavigationBar: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_material)
    }
}
