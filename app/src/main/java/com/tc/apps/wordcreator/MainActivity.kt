package com.tc.apps.wordcreator

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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

                setTextToButtons(letter1, buttonA)
                setTextToButtons(letter2, buttonB)
                setTextToButtons(letter3, buttonC)
                setTextToButtons(letter4, buttonD)
                setTextToButtons(letter5, buttonE)

            }
            splashViewModel = viewModel

            getAnswerFromButton(buttonA)
            getAnswerFromButton(buttonB)
            getAnswerFromButton(buttonC)
            getAnswerFromButton(buttonD)
            getAnswerFromButton(buttonE)

            newCharacters.setOnClickListener { viewModel.getButtonLetter() }
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
            if(viewModel.answer(getLetter(btn))){
                alert()
            }

        }
    }

    private fun alert(){
        val toast = Toast.makeText(this, "congrats", Toast.LENGTH_LONG)
        toast.setGravity(Gravity.TOP,0,0)
        toast.show()

    }

    private fun setTextToButtons(liveData: LiveData<String>, btn: Button){
        liveData.observe(this@MainActivity){
            value -> btn.text = value
        }
    }

}




