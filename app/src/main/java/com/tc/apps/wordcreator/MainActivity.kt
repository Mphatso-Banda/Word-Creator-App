package com.tc.apps.wordcreator

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.tc.apps.wordcreator.databinding.ActivityMainBinding
import com.tc.apps.wordcreator.viewmodels.SplashViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: SplashViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var mawu = StringBuilder()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.apply {
            getAnswerFromButton(buttonA)
            getAnswerFromButton(buttonB)
            getAnswerFromButton(buttonC)
            getAnswerFromButton(buttonD)
            getAnswerFromButton(buttonE)

            newCharacters.setOnClickListener {
                viewModel.getButtonLetter()
            }
        }

    }

    //Getting the letter of the button
    private fun getLetter(btn: Button): String{
        return btn.text.toString()
    }

    private fun getAnswerFromButton(btn: Button){
        btn.setOnClickListener {
            viewModel.answer(getLetter(btn))
        }
    }


}




