package com.tc.apps.wordcreator.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tc.apps.wordcreator.WordsContainer

class SplashViewModel() : ViewModel() {
    private val wordsContainer = WordsContainer()

    private val _score = MutableLiveData<Int>()
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

    val answer = StringBuilder()

    fun getButtonLetter() {
        reset()
        val nextWord = shuffleWord()

        _letter1.value = nextWord[0].toString()
        _letter2.value = nextWord[1].toString()
        _letter3.value = nextWord[2].toString()
        _letter4.value = nextWord[3].toString()
        _letter5.value = nextWord[4].toString()

    }

    // shuffle the word
    private fun shuffleWord(): CharArray {
        val word = getData().toCharArray()
        word.shuffle()
        Log.d("SPLASH", word[0].toString())
        return word
    }

    //returns a word from Words Container
    private fun getData(): String {
        val word = wordsContainer.getWords()

        val randomMap = word.random()
        val mapKey = randomMap.map { it.key }
//        val mapString = mapKey.toString()
//        Log.d("ViewModel", mapKey[0])
        return mapKey[0]
   }

    fun answer(s: String) {
        answer.append(s)
        _finalAnswer.value = answer.toString()
    }

    private fun reset(){
        answer.clear()
        _finalAnswer.value = ""
    }

    init {
        getButtonLetter()
        shuffleWord()
    }
}