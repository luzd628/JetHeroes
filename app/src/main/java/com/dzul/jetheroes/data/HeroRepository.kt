package com.dzul.jetheroes.data

import com.dzul.jetheroes.model.Hero
import com.dzul.jetheroes.model.HeroesData

class HeroRepository {
    fun getHeroes(): List<Hero>{
        return HeroesData.heroes
    }

    // fungsi utk mencari data pahlawam
    fun searchHeroes(query:String):List<Hero>{
        return HeroesData.heroes.filter {
            it.name.contains(query, ignoreCase = true)
            // ignoreCase berfungi utk pencarian tdk memperhatikan besar kecil hurufnya
        }
    }

}