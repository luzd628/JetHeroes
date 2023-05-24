package com.dzul.jetheroes.data

import com.dzul.jetheroes.model.Hero
import com.dzul.jetheroes.model.HeroesData

class HeroRepository {
    fun getHeroes(): List<Hero>{
        return HeroesData.heroes
    }

}