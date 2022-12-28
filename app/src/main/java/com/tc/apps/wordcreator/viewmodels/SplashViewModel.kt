package com.tc.apps.wordcreator.viewmodels

import android.util.Log
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tc.apps.wordcreator.MainActivity
import com.tc.apps.wordcreator.WordsContainer
import java.util.*

class SplashViewModel() : ViewModel() {
    private val wordsContainer = WordsContainer()
    private var words = listOf<String>()
    private val correctWords = mutableListOf<String>()

    private val _score = MutableLiveData<Int>(0)
    val score: LiveData<Int> get() = _score

    private val _letter1 = MutableLiveData<String>()
    val letter1: LiveData<String> get() = _letter1

    private val _letter2 = MutableLiveData<String>()
    val letter2: LiveData<String> get() = _letter2

    private val _letter3 = MutableLiveData<String>()
    val letter3: LiveData<String> get() = _letter3

    private val _letter4 = MutableLiveData<String>()
    val letter4: LiveData<String> get() = _letter4

    private val _letter5 = MutableLiveData<String>()
    val letter5: LiveData<String> get() = _letter5

    private val _finalAnswer = MutableLiveData<String>()
    val finalAnswer: LiveData<String> get() = _finalAnswer

    private var answer = StringBuilder()

    fun getText():LiveData<String>{
        return finalAnswer
    }

    fun getButtonLetter() {
        reset()
        correctWords.clear()
        val nextWord = shuffleWord()
        Log.d("_LETTER1", nextWord[0].toString())

        _letter1.value = nextWord[0].toString()
        Log.d("LETTER1", letter1.value.toString())

        _letter2.value = nextWord[1].toString()
        _letter3.value = nextWord[2].toString()
        _letter4.value = nextWord[3].toString()
        _letter5.value = nextWord[4].toString()

    }

    // shuffle the word
    private fun shuffleWord(): CharArray {
        val word = getData().toCharArray()
        word.shuffle()
        Log.d("SHUFFLE", word[0].toString())
        return word
    }

    //returns a word from Words Container
    private fun getData(): String {
        val word = wordsContainer.getWords()

        val randomMap = word.random()
        val mapKey = randomMap.map { it.key }

        val mapValue = randomMap.map { it.value }
        words = mapValue[0]
        Log.d("WORD", mapKey[0].toString())
        return mapKey[0]
   }

    fun answer(s: String){
        answer.append(s)
        _finalAnswer.value = answer.toString()
        Log.d("Answer", finalAnswer.value.toString())

    }

    fun checkAnswer():Boolean{
        Log.d("Check Ans", "$correctWords[]")
        if(!correctWords.contains(answer.toString().lowercase(Locale.ROOT))){
            if(words.contains(answer.toString().lowercase(Locale.ROOT))){
                //Correct word

                Log.d("Check Ans", "Correct Word Sucka!")
                reset()
                correctWords.add(answer.toString().lowercase(Locale.ROOT))
                return true
            }
            else{
                Log.d("Check Ans", "Wapala Sucka!")
                return false
            }
        }else{
            Log.d("Check Ans", "Ilimo kale!")
            return false
        }
    }

    fun clearLetter() : String?{
        var s: String? = null
        if(answer.isNotEmpty()){
            s = answer[answer.length - 1].toString()
            answer = StringBuilder(answer.substring(0, answer.length -1))
            _finalAnswer.value = answer.toString()
            return s
        }
        else{
            return s
        }
    }
    fun clearAnswer():Boolean{
        answer.clear()
        _finalAnswer.value = ""
        return true
    }

    private fun reset() {
        answer.clear()
        _finalAnswer.value = ""

    }


    init {
        getButtonLetter()
//        shuffleWord()
    }
}