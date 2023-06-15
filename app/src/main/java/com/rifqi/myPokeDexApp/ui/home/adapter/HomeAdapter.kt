package com.rifqi.myPokeDexApp.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rifqi.core.model.PokemonResponse
import com.rifqi.myPokeDexApp.databinding.ItemListPokemonBinding

class HomeAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val pokemonList = ArrayList<PokemonResponse.PokemonItem>()
    var onItemClickCallback: ((data: PokemonResponse.PokemonItem) -> Unit)? = null

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
                ivDelete.isVisible = false
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
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        (holder as HomeAdapter.PokemonViewHolder).setData(pokemonList[position])

    override fun getItemCount(): Int = pokemonList.size

    fun updateData(pokemons: ArrayList<PokemonResponse.PokemonItem>) {
        pokemonList.clear()
        pokemonList.addAll(pokemons)
        notifyDataSetChanged()
    }
}
