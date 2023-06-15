package com.rifqi.core.model

data class PokemonResponse(
    val next: String,
    val previous: Any,
    val count: Int,
    val results: ArrayList<PokemonItem>
) {
    data class PokemonItem(
        val name: String,
        val url: String,
    )
}