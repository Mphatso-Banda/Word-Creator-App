package com.tc.apps.wordcreator

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.tc.apps.wordcreator.databinding.ActivityMainBinding
import com.tc.apps.wordcreator.viewmodels.SplashViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: SplashViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {

            viewModel.apply {
                finalAnswer.observe(this@MainActivity){
                        value ->  answer.text  = value
                }

                letter1.observe(this@MainActivity){
                    value -> buttonA.text = value
                }
                letter2.observe(this@MainActivity){
                        value -> buttonB.text = value
                }
                letter3.observe(this@MainActivity){
                        value -> buttonC.text = value
                }
                letter4.observe(this@MainActivity){
                        value -> buttonD.text = value
                }
                letter5.observe(this@MainActivity){
                        value -> buttonE.text = value
                }
            }
            splashViewModel = viewModel

            getAnswerFromButton(buttonA)
            getAnswerFromButton(buttonB)
            getAnswerFromButton(buttonC)
            getAnswerFromButton(buttonD)
            getAnswerFromButton(buttonE)


        }
    }

    //Getting the letter of the button
    private fun getLetter(btn: Button): String{
        Log.d("Button", btn.text.toString())
        return btn.text.toString()
    }

    //General function to set the actions for the buttons
    private fun getAnswerFromButton(btn: Button){
        btn.setOnClickListener {
            viewModel.answer(getLetter(btn))
        }
    }

}




