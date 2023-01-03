package com.tc.apps.wordcreator.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tc.apps.wordcreator.SplashScreen
import com.tc.apps.wordcreator.WordsContainer
import kotlinx.coroutines.launch
import java.util.*

class SplashViewModel() : ViewModel() {
    private val wordsContainer = WordsContainer()
    private var words = listOf<String>()
    private val correctWords = mutableListOf<String>()
    private var scoreCount = 0
    private var dictionary = mutableListOf<String>()

    private var listOfLetters = mutableListOf<MutableLiveData<String>>()

    private val _score = MutableLiveData<Int>(0)
    val score: LiveData<Int> get() = _score

    //TODO("Testing umaziwa")
    fun LiveData<String>.state(txt: String, boo: Boolean): Boolean {
        //val useLess = this.hasActiveObservers()
        getBtnToDealWith(txt)
        return boo
    }

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

    private val _letter6 = MutableLiveData<String>()
    val letter6: LiveData<String> get() = _letter6

    private val _letter7 = MutableLiveData<String>()
    val letter7: LiveData<String> get() = _letter7

    private val _letter8 = MutableLiveData<String>()
    val letter8: LiveData<String> get() = _letter8

    private val _letter9 = MutableLiveData<String>()
    val letter9: LiveData<String> get() = _letter9



    private val _finalAnswer = MutableLiveData<String>()
    val finalAnswer: LiveData<String> get() = _finalAnswer

    private var answer = StringBuilder()

    private val btnList = mutableListOf<LiveData<String>>()

    fun getText():LiveData<String>{
        return finalAnswer
    }

    //TODO("done already")
    fun getBtnToDealWith(txt: String){
        for ((position, letter) in btnList.withIndex()){
            if (letter.value == txt){
                Log.d("Get Position", btnList[position].toString())
            }else
                Log.d("Get Position", "mbola man $position")
        }
    }

    fun getButtonLetter() {
        reset()
        correctWords.clear()
        val nextWord = shuffleWord()

        for((position, letter) in listOfLetters.shuffled().withIndex()){

            if(position < nextWord.size){
                letter.value = nextWord[position].toString()
            }
            else{
                letter.value = null
            }
        }
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
        val word = wordsContainer.getWords(dictionary)

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
            return if(words.contains(answer.toString().lowercase(Locale.ROOT))){
                //Correct word

                Log.d("Check Ans", "Correct Word Sucka!")
                increasePoints()
                Log.d("score", score.value.toString())
                reset()
                true
            } else{
                if(dictionary.contains(answer.toString())){
                    increasePoints()
                    true
                }
                else{
                    false
                }
            }
        }else{
            Log.d("Check Ans", "Ilimo kale!")
            return false
        }
    }

    fun clearLetter() : String?{
        var s: String? = null
        return if(answer.isNotEmpty()){
            s = answer[answer.length - 1].toString()
            answer = StringBuilder(answer.substring(0, answer.length -1))
            _finalAnswer.value = answer.toString()
            s
        } else{
            s
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

    private fun addButtonsToList(){
        listOfLetters += mutableListOf(_letter1, _letter2, _letter3, _letter4,
            _letter5, _letter6, _letter7, _letter8, _letter9)
    }

    private fun increasePoints(){
        scoreCount += answer.length
        _score.value = scoreCount
        correctWords.add(answer.toString().lowercase(Locale.ROOT))
    }

    init {
        dictionary = SplashScreen.getDictionary()
        addButtonsToList()
        viewModelScope.launch{
            getButtonLetter()
        }


        //btnList += listOf(letter1,letter2, letter3, letter4, letter5)
//        shuffleWord()
    }
}