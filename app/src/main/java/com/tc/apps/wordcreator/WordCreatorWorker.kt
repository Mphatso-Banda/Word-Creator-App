package com.tc.apps.wordcreator

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import java.util.*
//for later
class WordCreatorWorker(context: Context, workerParams: WorkerParameters): Worker(context, workerParams) {
    private val MAX: Int = 256
    override fun doWork(): Result {

        val output = workDataOf("is_success" to generatedWords(SplashScreen.getDictionary()))
        return Result.success(output)
    }


    private fun generatedWords(list: MutableList<String>) : Boolean{
        val returnList = mutableListOf<Map<String, List<String>>>()

        for (i in 1..1000){
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

        val count = IntArray(MAX)
        val str3 = mawu.lowercase(Locale.ROOT).toCharArray()

        for (i in str3.indices) {
            count[str3[i].code]++
        }

        for (word in dictionary){

            if(canMakeStr2(count, word.lowercase(Locale.getDefault()))){
                subString.add(word)
            }

        }

        return subString
    }

    private fun canMakeStr2(count: IntArray, str2: String): Boolean {
//        val count = IntArray(MAX)
//        val str3 = str1.toCharArray()
//
//        for (i in str3.indices)
//            count[str3[i].code]++

        val str4 = str2.toCharArray()

        for (i in str4.indices) {
            if (count[str4[i].code] == 0) return false
            count[str4[i].code]--
        }
        return true
    }

}