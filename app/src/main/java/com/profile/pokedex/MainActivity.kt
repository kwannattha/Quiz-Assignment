package com.profile.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.profile.pokedex.data.api.APIInterface
import com.profile.pokedex.data.repository.PokemonRepo

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var recyclerViewAdapter = RecyclerViewAdapter()
        var recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter =  recyclerViewAdapter

        val retrofitService = APIInterface.create()
        val pokemonRepo = PokemonRepo(retrofitService)
        var viewModel = ViewModelProvider(this,
            MainActivityViewModelFactory(pokemonRepo))
                .get(MainActivityViewModel::class.java)

        viewModel.pokemonListItem.observe(this,{
            recyclerViewAdapter.setPokemonList(it)
        })
        viewModel.loading.observe(this, {
            val pgBar = findViewById<ProgressBar>(R.id.progressBar)
            pgBar.visibility = if (it) View.VISIBLE else View.GONE
        })
        viewModel.loadPokemonData()


    }


}