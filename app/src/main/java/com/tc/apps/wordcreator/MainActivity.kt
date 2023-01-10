package com.tc.apps.wordcreator

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.graphics.drawable.AnimatedVectorDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.tc.apps.wordcreator.databinding.ActivityMainBinding
import com.tc.apps.wordcreator.viewmodels.SplashViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: SplashViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    lateinit var animeButton: Button

    private var buttonsMap = mapOf<LiveData<String>, Button>()

    private lateinit var imgVector: ImageView

    private var media = MediaPlayer()
    private var generatedWords = mapOf<String, Any>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val constraints = Constraints.Builder()
//            .setRequiresBatteryNotLow(true)
//            .setRequiresStorageNotLow(true)
//            .build()
//
//        val oneTimeWorkRequest = OneTimeWorkRequestBuilder<WordCreatorWorker>()
//            .setConstraints(constraints)
//            .build()
//
//        WorkManager.getInstance(this).enqueue(oneTimeWorkRequest)
//
//        WorkManager.getInstance(this)
//            .getWorkInfoByIdLiveData(oneTimeWorkRequest.id).observe(this, Observer { workInfo ->
//                if (workInfo.state == WorkInfo.State.SUCCEEDED){
//                    Log.d("worker task complete", "Zatheka chanichani")
//                }
//            })

        media = MediaPlayer.create(this, R.raw.game_music)
        media.start()

        media.setOnCompletionListener {
            media.start()
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        GlobalScope.launch{
//            val obj = Json.encodeToString(SplashScreen.getDictionary())
//
//        }

        binding.apply {
            splashViewModel = viewModel

            imgVector = imageVector

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
                                    fadeIn(button)
                                }
                                else{
                                    button.isEnabled = true
                                    fadeIn(button)
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

                for ((data, button) in buttonsMap){
                    animeButton = button
                    rotater()
                }
                progressBar3.isVisible = true
                viewModel.getButtonLetter()
                progressBar3.isVisible = false
                enableButton(buttonsMap)
                setButtonState()
                val randColor = Color((0..256).random(), (0..256).random(), (0..256).random()).toArgb()
                newCharacters.setBackgroundColor(randColor)
            }
        }
        imgVector.isVisible = false

    }

    override fun onPause() {
        super.onPause()
        if(media.isPlaying){
            media.pause()
        }

    }

    override fun onResume() {
        super.onResume()
        if(!media.isPlaying){
            media.start()
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

    private fun rotater() {
        val animator = ObjectAnimator.ofFloat(animeButton, View.ROTATION, -360f, 0f)
        animator.duration = 1000
        animator.disableViewDuringAnimation(binding.newCharacters)
        animator.start()
    }

    private fun faderOut(btn: Button) {
        val animator = ObjectAnimator.ofFloat(btn, View.ALPHA, 0f)
        animator.disableViewDuringAnimation(btn)
        animator.start()
    }

    private fun fadeIn(btn: Button){
        val animator = ObjectAnimator.ofFloat(btn, View.ALPHA, 1f)
        animator.disableViewDuringAnimation(btn)
        animator.start()
    }

    private fun ObjectAnimator.disableViewDuringAnimation(view: View) {
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                view.isEnabled = false
            }

            override fun onAnimationEnd(animation: Animator?) {
                view.isEnabled = true
            }
        })
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
            fadeIn(button)
        }
    }

    //General function to set the actions for the buttons
    private fun getAnswerFromButton(btn: Button){
        btn.setOnClickListener {
            viewModel.answer(getLetter(btn))
            faderOut(btn)
            btn.isEnabled = false
        }
    }

    //checkAnswer
    private fun checkAnswer(){

        viewModel.apply{

            when(checkAnswer()){
                1 -> {
                    binding.imageVector.setImageResource(R.drawable.anime_vector)
                    correctAlert()
                    enableButton(buttonsMap)
                    showAnimation()
                }
                2 -> {
                    binding.imageVector.apply {
                        setImageResource(R.drawable.anime_used)
                    }
                    alreadyUsedAlert()
                    enableButton(buttonsMap)
                    showAnimation()
                }
                else -> {
                    binding.imageVector.apply {
                        setImageResource(R.drawable.animate_failed)
                    }
                    enableButton(buttonsMap)
                    failedAlert()
                    showAnimation()
                }
            }

        }

    }

    private fun showAnimation(){
        imgVector.isVisible = true
        val anime: AnimatedVectorDrawable = imgVector.drawable as AnimatedVectorDrawable
        anime.start()
        imgVector.animate().setDuration(2000).alpha(1f).withEndAction{
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            imgVector.isVisible = false
        }
    }

    private fun correctAlert(){
        val toast = Toast.makeText(this, getText(R.string.congratsText), Toast.LENGTH_LONG)
        toast.setGravity(Gravity.TOP,0,0)
        toast.show()

    }

    private fun failedAlert(){
        val toast = Toast.makeText(this, getText(R.string.failedText), Toast.LENGTH_LONG)
        toast.setGravity(Gravity.TOP,0,0)
        toast.show()

    }

    private fun alreadyUsedAlert(){
        val toast = Toast.makeText(this, getText(R.string.wordUsedText), Toast.LENGTH_LONG)
        toast.setGravity(Gravity.TOP,0,0)
        toast.show()

    }

    private fun setTextToButtons(liveData: LiveData<String>, btn: Button){
        liveData.observe(this@MainActivity){
            value -> btn.text = value
        }
    }

    private fun startPlaying(){

    }

    private fun isExternalStorageReadOnly(): Boolean {
        val extStorageState = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED_READ_ONLY == extStorageState
    }

    private fun isExternalStorageAvailable(): Boolean {
        val extStorageState = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == extStorageState
    }


}




