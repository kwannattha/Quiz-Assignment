package com.profile.pokedex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.profile.pokedex.data.model.ListItem

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    var pokemonListItem : List<ListItem> = listOf()
    fun setPokemonList(data: List<ListItem>){
        this.pokemonListItem = data
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val namepokemon: TextView = itemView.findViewById(R.id.namepokemon)
        val imagepokemon : ImageView = itemView.findViewById(R.id.imagepokemon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item,
            parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.namepokemon.text = this.pokemonListItem.get(position).name
        Glide.with(holder.imagepokemon.context)
            .load(this.pokemonListItem.get(position).imgUrl)
            .into(holder.imagepokemon)
    }

    override fun getItemCount(): Int {
        return this.pokemonListItem.size
    }


}