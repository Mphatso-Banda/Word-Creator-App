package com.tc.apps.wordcreator

import android.content.Context
import android.content.Intent
import android.content.res.AssetManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.work.*
import androidx.lifecycle.Observer
import java.text.FieldPosition
import java.util.*

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)



        var imageView = findViewById<ImageView>(R.id.imageView2)

        springAnimation(imageView, 50f)
        //springAnimation.start()
        imageView.alpha = 0f
        imageView.animate().setDuration(1000).alpha(1f).withEndAction{
            initializeDictionary(this)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }

    fun springAnimation(view: View, position: Float){
        val sa = SpringAnimation(view, DynamicAnimation.TRANSLATION_Y)
        val sf = SpringForce()
        sf.stiffness = SpringForce.STIFFNESS_LOW
        sf.finalPosition = position
        sf.dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
        sa.spring = sf
        sa.start()
    }


    companion object WordsDictionary{

        private val dictionary = mutableListOf<String>()
        fun initializeDictionary(context: Context){
            val assetManager: AssetManager = context.assets
            val wordpath = assetManager.open("words.txt")

            //WordsContainer()

            val start = System.currentTimeMillis()

            wordpath.reader().useLines { lines ->
                lines.forEach {
                    dictionary.add(it.lowercase(Locale.getDefault()))
                }
            }
            val end = System.currentTimeMillis() - start

            Log.d("Time Elapsed:", end.toString())
        }

        fun getDictionary(): MutableList<String>{
            return this.dictionary
        }
    }
}