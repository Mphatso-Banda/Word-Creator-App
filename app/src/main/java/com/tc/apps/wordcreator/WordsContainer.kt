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


    private fun selectWord(dictionary: MutableList<String>): String {
        var randomWord = dictionary.random()
        return if (cleanWord(randomWord)){
            randomWord
        }
        else{
            selectWord(dictionary)
        }

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

    private fun checkUpperCase(word: String): Boolean{
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

    private fun String?.indexesOf(substr: String, ignoreCase: Boolean = true): List<Int> {
        return this?.let {
            val regex = if (ignoreCase) Regex(substr, RegexOption.IGNORE_CASE) else Regex(substr)
            regex.findAll(this).map { it.range.start }.toList()
        } ?: emptyList()
    }

    fun cleanWord(word: String) : Boolean{
        return (word.length in 3..9
                && !(checkUpperCase(word))
                && !(word.contains("[0-9]".toRegex()))
                && !(word.contains("[!\"#$%&'()*+,-./:;\\\\<=>?@\\[\\]^_`{|}~]".toRegex())))
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