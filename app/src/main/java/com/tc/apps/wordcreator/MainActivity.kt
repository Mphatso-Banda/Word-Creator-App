package com.tc.apps.wordcreator

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tc.apps.wordcreator.databinding.ActivityMainBinding
import com.tc.apps.wordcreator.viewmodels.SplashViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: SplashViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    private var buttonsMap = mapOf<LiveData<String>, Button>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            splashViewModel = viewModel


            viewModel.apply {
                finalAnswer.observe(this@MainActivity){
                        value ->  answer.setText(value)
                }

                buttonsMap += mapOf(letter1 to buttonA, letter2 to buttonB, letter3 to buttonC,
                    letter4 to buttonD, letter5 to buttonE, letter6 to buttonF, letter7 to buttonG,
                    letter8 to buttonH, letter9 to buttonI)

                buttonsMap.map { it.key to it.value }.shuffled().toMap()
                setButtonState()

                score.observe(this@MainActivity){
                    value -> binding.score.text = "Score: ${value.toString()}"
                }
            }

            checkBtn.setOnClickListener {
                checkAnswer()
            }

            clearBtn.apply {
                setOnClickListener{
                    val s = viewModel.clearLetter()
                    for((liveData, button) in buttonsMap){
                        if(s != null){
                            if(button.text == s){
                                if(button.isEnabled){
                                    button.isEnabled = true
                                }
                                else{
                                    button.isEnabled = true
                                    break
                                }
                            }
                        }
                    }
                }
                setOnLongClickListener {
                    enableButton(buttonsMap)
                    viewModel.clearAnswer()
                }
            }
            for ((liveData, button) in buttonsMap){
                getAnswerFromButton(button)
            }

            newCharacters.setOnClickListener {
                viewModel.getButtonLetter()
                enableButton(buttonsMap)
                setButtonState()
                val randColor = Color((0..256).random(), (0..256).random(), (0..256).random()).toArgb()
                newCharacters.setBackgroundColor(randColor)
            }
        }

    }

    private fun setButtonState(){
        viewModel.apply {
            for ((liveData, button) in buttonsMap){
                if(liveData.value != null){
                    setTextToButtons(liveData, button)
                    button.isVisible = true
                    val randColor = Color((0..256).random(), (0..256).random(), (0..256).random()).toArgb()
                    button.setBackgroundColor(randColor)
                }
                else{
                    button.isVisible = false
                }

            }
        }
    }

    //Getting the letter of the button
    private fun getLetter(btn: Button): String{
        Log.d("Button", btn.text.toString())
        return btn.text.toString()
    }

    //reset button state
    private fun enableButton(map: Map<LiveData<String>, Button>){
        for ((liveData, button) in map){
            button.isEnabled = true
        }
    }

    //General function to set the actions for the buttons
    private fun getAnswerFromButton(btn: Button){
        btn.setOnClickListener {
            viewModel.answer(getLetter(btn))
            btn.isEnabled = false
        }
    }

    //checkAnswer
    private fun checkAnswer(){
        if(viewModel.checkAnswer()){
            correctAlert()
            enableButton(buttonsMap)
        }
        else{
            failedAlert()
        }
    }

    private fun correctAlert(){
        val toast = Toast.makeText(this, "Congrats", Toast.LENGTH_LONG)
        toast.setGravity(Gravity.TOP,0,0)
        toast.show()

    }

    private fun failedAlert(){
        val toast = Toast.makeText(this, "Failed", Toast.LENGTH_LONG)
        toast.setGravity(Gravity.TOP,0,0)
        toast.show()

    }

    private fun setTextToButtons(liveData: LiveData<String>, btn: Button){
        liveData.observe(this@MainActivity){
            value -> btn.text = value
        }
    }

}




