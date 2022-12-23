package com.tc.apps.wordcreator

class WordsContainer {

    var list = mutableListOf<Map<String, List<String>>>()
    val map2 = mapOf("DENDP" to listOf<String>("depend","deep","end"))
    val map3 = mapOf("EVNTS" to listOf<String>("events","eve","ten","seven","see","sent"))
    init {
        list.add(map2)
        list.add(map3)
    }

    public fun getWords(): List<Map<String, List<String>>>{
        return list
    }

}