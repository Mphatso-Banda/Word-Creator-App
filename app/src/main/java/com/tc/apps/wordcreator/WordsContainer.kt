package com.tc.apps.wordcreator

import java.util.*

class WordsContainer {

    var list = mutableListOf<Map<String, List<String>>>()


    fun getWords(dictionary: MutableList<String>): List<Map<String, List<String>>>{
        val liwu = selectWord(dictionary)
        val mawuAmbiri = checkAnagrams(liwu, dictionary)
        list.clear()
        list.add(mapOf(liwu to mawuAmbiri))
        return list
    }


    fun selectWord(dictionary: MutableList<String>): String {
        var randomWord = dictionary.random()
        return if (cleanWord(randomWord)){
            randomWord
        }
        else{
            selectWord(dictionary)
        }

    }

    fun checkAnagrams(mawu: String, dictionary: MutableList<String>) : MutableList<String> {

        val subString = mutableListOf<String>()

        for (word in dictionary){
            if(cleanWord(word)){
                if(ifStringInString(mawu, word.lowercase(Locale.getDefault()))){
                    subString.add(word)
                }
            }
        }

        return subString
    }

    fun ifStringInString(mawu: String, dictionaryWord: String): Boolean{
        val list = CharArray(dictionaryWord.length)
        val found = mutableListOf<Int>()

        for ((i, c) in dictionaryWord.withIndex()){
            for((j, s) in mawu.withIndex()){
                if(found.contains(j)){
                    continue
                }
                else{
                    if(c == s){
                        found.add(j)
                        list[i] = c
                        break
                    }
                }

            }
        }
// Needs refactoring
//    val map = mutableMapOf<Int, Char>()
//
//    for((position, value ) in dictionaryWord.withIndex()){
//        if(mawu.contains(value)){
//            if(){
//                list[position] = value
//                map[mawu.indexOf(value)] = value
//            }
//        }
//    }
        return dictionaryWord == String(list)
    }

    fun checkUpperCase(word: String): Boolean{
        var boolean : Boolean = false
        for (c in word){
            if(c.isUpperCase()){
                boolean = true
            }
            else{
                boolean = false
                break
            }
        }
        return boolean
    }

    fun cleanWord(word: String) : Boolean{
        return (word.length in 3..9
                && !(checkUpperCase(word))
                && !(word.contains("[0-9]".toRegex()))
                && !(word.contains("[!\"#$%&'()*+,-./:;\\\\<=>?@\\[\\]^_`{|}~]".toRegex())))
    }
}