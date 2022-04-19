package com.profile.pokedex

import android.os.Message
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.profile.pokedex.data.api.model.Result
import com.profile.pokedex.data.model.ListItem
import com.profile.pokedex.data.repository.PokemonRepo
import kotlinx.coroutines.*
import okhttp3.Dispatcher

class MainActivityViewModel constructor(private val repo : PokemonRepo)
    : ViewModel() {
    val pokemonList = MutableLiveData<List<Result>>()
    val pokemonListItem = MutableLiveData<List<ListItem>>()
    val errorMessage = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()
    var job: Job? = null

    fun loadPokemonData(){
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repo.getPokemonList()
            withContext(Dispatchers.Main){
                if (response.isSuccessful) {
                    val data = response.body()
                    pokemonList.postValue(data!!.results)
                    loading.value = false
                    val item = response.body()!!.results.mapIndexed { index, result ->
                       val pID = if(result.url.endsWith("/")){
                            result.url.dropLast(1).takeLastWhile {
                                it.isDigit()
                            }
                        }else{
                            result.url.takeLastWhile { it.isDigit() }
                        }
                        val imgUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/21${pID}.png"
                        ListItem(result.name, result.url, imgUrl)
                    }
                    pokemonListItem.postValue(item)
                    loading.value = false
                }else{
                    onError(response.message())
                }
            }
        }
    }
    private fun onError(message: String){
        errorMessage.value = message
        loading.value = false
    }
}