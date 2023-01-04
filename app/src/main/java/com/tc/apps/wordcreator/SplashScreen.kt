package com.tc.apps.wordcreator

import android.content.Context
import android.content.Intent
import android.content.res.AssetManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import java.util.*

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)



        var imageView = findViewById<ImageView>(R.id.imageView2) as ImageView

        imageView.alpha = 0f
        imageView.animate().setDuration(10).alpha(1f).withEndAction{
            initializeDictionary(this)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }

    companion object WordsDictionary{

        private val dictionary = mutableListOf<String>()
        fun initializeDictionary(context: Context){
            val assetManager: AssetManager = context.assets
            val wordpath = assetManager.open("words.txt")

            val wordsContainer = WordsContainer()
            wordpath.reader().useLines { lines ->
                lines.forEach {
                    if(wordsContainer.cleanWord(it))
                        dictionary.add(it.lowercase(Locale.getDefault()))
                }
            }
        }

        fun getDictionary(): MutableList<String>{
            return this.dictionary
        }
    }
}