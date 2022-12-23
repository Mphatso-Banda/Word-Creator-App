package com.tc.apps.wordcreator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.io.File

private const val FILE_PATH = ""

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Words.setWords()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    companion object Words{
        private var words: List<String>? = null

        fun setWords(){
            fun readFile(): List<String> = File(FILE_PATH).useLines { it.toList() }
            words = readFile()
        }

        fun getWords(): List<String>?{
            return words
        }
    }
}