package com.rifqi.myPokeDexApp.ui.mypokemon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rifqi.core.model.PokemonResponse
import com.rifqi.myPokeDexApp.databinding.ItemListPokemonBinding

class MyPokemonAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val pokemonList = ArrayList<PokemonResponse.PokemonItem>()
    var onItemClickCallback: ((data: PokemonResponse.PokemonItem) -> Unit)? = null
    var onItemDeleteCallback: ((data: PokemonResponse.PokemonItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ItemListPokemonBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(view)
    }

    inner class PokemonViewHolder(
        private val binding: ItemListPokemonBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun setData(data: PokemonResponse.PokemonItem) {
            binding.apply {
                ivDelete.isVisible = true
                val pokemonImage = data.url
                val number = if (pokemonImage.endsWith("/")) {
                    pokemonImage.dropLast(1).takeLastWhile { it.isDigit() }
                } else {
                    pokemonImage.takeLastWhile { it.isDigit() }
                }
                val url =
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
                binding.apply {
                    Glide.with(itemView)
                        .load(url)
                        .into(ivPokemon)
                    tvPokemonName.text = data.name
                }

                cvPokemon.setOnClickListener {
                    onItemClickCallback?.invoke(data)
                }

                ivDelete.setOnClickListener {
                    onItemDeleteCallback?.invoke(data)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        (holder as MyPokemonAdapter.PokemonViewHolder).setData(pokemonList[position])

    override fun getItemCount(): Int = pokemonList.size

    fun updateData(pokemons: ArrayList<PokemonResponse.PokemonItem>) {
        pokemonList.clear()
        pokemonList.addAll(pokemons)
        notifyDataSetChanged()
    }
}
