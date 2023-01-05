package com.tc.apps.wordcreator

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import java.util.*
//for later
class WordCreatorWorker(context: Context, workerParams: WorkerParameters): Worker(context, workerParams) {
    override fun doWork(): Result {

        val output = workDataOf("is_success" to generatedWords(SplashScreen.getDictionary()))
        return Result.success(output)
    }


    private fun generatedWords(list: MutableList<String>) : Boolean{
        val returnList = mutableListOf<Map<String, List<String>>>()

        for (i in 1..10){
            val mawu = selectWord(list)
            returnList.add(mapOf(mawu to checkAnagrams(mawu, list)))

            Log.d("Generated Word:", mawu)
        }

        return true
    }


    private fun selectWord(dictionary: MutableList<String>): String {
        return dictionary.random()
    }

    private fun checkAnagrams(mawu: String, dictionary: MutableList<String>) : MutableList<String> {

        val subString = mutableListOf<String>()

        for (word in dictionary){

            if(ifStringInString(mawu, word.lowercase(Locale.getDefault()))){
                subString.add(word)
            }

        }

        return subString
    }

    private fun ifStringInString(mawu: String, dictionaryWord: String): Boolean{
        val list = CharArray(dictionaryWord.length)
        val found = mutableListOf<Int>()

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

        for((position, value ) in dictionaryWord.withIndex()){
            if(mawu.contains(value)){
                if(!map.containsKey(getLastIndex(map, value))){
                    list[position] = value
                    map[mawu.indexOf(value)] = value
                }
                else{
                    if(mawu.indexOf(value, getLastIndex(map, value) + 1) != -1){
                        list[position] = value
                        map[mawu.indexOf(value, getLastIndex(map, value) + 1)] = value
                    }
                }
            }
        }
        return dictionaryWord == String(list)
    }

    private fun getLastIndex(map: Map<Int, Char>, value: Char) : Int{
        var lastIndex: Int = 0
        for ((k,v) in map){
            if(value == v){
                lastIndex = k
            }
        }

        return lastIndex
    }
}