package com.dzul.jetheroes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dzul.jetheroes.data.HeroRepository
import com.dzul.jetheroes.model.Hero
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class JetHeroesViewModel(private val repository: HeroRepository) : ViewModel() {

    private val _groupedHeroes = MutableStateFlow(
        repository.getHeroes()
            .sortedBy { it.name }
            .groupBy { it.name[0] }
    )

    val groupedHeroes : StateFlow<Map<Char,List<Hero>>> get() = _groupedHeroes

    //variabel & funsi pencarian
    private val _query = mutableStateOf("")
    val query:State<String> get() = _query

    fun search(newQuery:String){
        _query.value = newQuery
        _groupedHeroes.value = repository.searchHeroes(_query.value)
            .sortedBy { it.name }
            .groupBy { it.name[0] }
    }

}


//kode diatas menggunakan mekanisme backing property,untk mencegah perubahah dari luar viewmodel

//dan data disimpan sebagai stateflow supaya data dpt terupdate ketika terjadi perubahan
//yang sebelumnya anda sering menggunakan livedata

//instance viewmodel:
class ViewModelFactory(private val repository: HeroRepository): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JetHeroesViewModel::class.java)) {
            return JetHeroesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}

