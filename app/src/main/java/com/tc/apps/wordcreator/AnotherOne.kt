package com.tc.apps.wordcreator

import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.tc.apps.wordcreator.databinding.ActivityAnotherOneBinding
import com.tc.apps.wordcreator.viewmodels.SplashViewModel

class AnotherOne : AppCompatActivity() {

    private val thisModel: SplashViewModel by viewModels()
    private lateinit var binding: ActivityAnotherOneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAnotherOneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)


        }
    }