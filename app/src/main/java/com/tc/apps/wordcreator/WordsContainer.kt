package com.tc.apps.wordcreator

import android.util.Log
import java.util.*

class WordsContainer {

    var list = mutableListOf<Map<String, List<String>>>()


    fun getWords(dictionary: MutableList<String>): List<Map<String, List<String>>> {
        val liwu = selectWord(dictionary)
        val mawuAmbiri = checkAnagrams(liwu, dictionary)
        list.clear()
        list.add(mapOf(liwu to mawuAmbiri))
        return list
    }


    private fun selectWord(dictionary: MutableList<String>): String {
        return dictionary.random()
    }

    private fun checkAnagrams(mawu: String, dictionary: MutableList<String>): MutableList<String> {

        val subString = mutableListOf<String>()

        val time = System.currentTimeMillis()
        for (word in dictionary) {
            if (canMakeStr2(mawu.lowercase(Locale.getDefault()), word.lowercase(Locale.getDefault()))) {
                subString.add(word)
            }
//            if (ifStringInString(mawu.lowercase(Locale.getDefault()), word.lowercase(Locale.getDefault()))) {
//                subString.add(word)
//            }
        }

        val elapsedTime = System.currentTimeMillis() - time

        Log.d("nthawi chanichani", elapsedTime.toString())

        return subString
    }

    private fun canMakeStr2(str1: String, str2: String): Boolean {
        val count = IntArray(256)
        val str3 = str1.toCharArray()

        for (i in str3.indices)
            count[str3[i].code]++

        val str4 = str2.toCharArray()

        for (i in str4.indices) {
            if (count[str4[i].code] == 0) return false
            count[str4[i].code]--
        }
        return true
    }


    private fun ifStringInString(mawu: String, dictionaryWord: String): Boolean {
        val list = CharArray(dictionaryWord.length)
        val found = mutableListOf<Int>()

        //


//        for ((i, c) in dictionaryWord.withIndex()){
//            for((j, s) in mawu.withIndex()){
//                if(found.contains(j)){
//                    continue
//                }
//                else{
//                    if(c == s){
//                        found.add(j)
//                        list[i] = c
//                        break
//                    }
//                }
//
//            }
//        }
//
//    val map = mutableMapOf<Int, Char>()
//
//    for((position, value ) in dictionaryWord.withIndex()){
//        if(mawu.contains(value)){
//            if(map.containsKey(mawu.indexOf(value)) && map.containsValue(value)){
//                list[position] = value
//                map[mawu.indexOf(value)] = value
//            }
//        }
//    }
//        return dictionaryWord == String(list)

        val map = mutableMapOf<Int, Char>()

        for ((position, value) in dictionaryWord.withIndex()) {
            if (mawu.contains(value)) {
                if (!map.containsKey(getLastIndex(map, value))) {
                    list[position] = value
                    map[mawu.indexOf(value)] = value
                } else {
                    if (mawu.indexOf(value, getLastIndex(map, value) + 1) != -1) {
                        list[position] = value
                        map[mawu.indexOf(value, getLastIndex(map, value) + 1)] = value
                    }
                }
            }
        }
        return dictionaryWord == String(list)
    }



    private fun checkUpperCase(word: String): Boolean {
        var boolean: Boolean = false
        for (c in word) {
            if (c.isUpperCase()) {
                boolean = true
            } else {
                boolean = false
                break
            }
        }
        return boolean
    }


    private fun getLastIndex(map: Map<Int, Char>, value: Char): Int {
        var lastIndex: Int = 0
        for ((k, v) in map) {
            if (value == v) {
                lastIndex = k
            }
        }

        return lastIndex
    }


}