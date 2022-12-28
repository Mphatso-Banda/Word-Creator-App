package com.tc.apps.wordcreator

class WordsContainer {

    var list = mutableListOf<Map<String, List<String>>>()
    val map2 = mapOf("DENEP" to listOf<String>("depend","deep","end","den"))
    val map3 = mapOf("EVNTE" to listOf<String>("events","eve","ten"))
    init {
        list.add(map2)
        list.add(map3)
    }

    public fun getWords(): List<Map<String, List<String>>>{
        return list
    }

}