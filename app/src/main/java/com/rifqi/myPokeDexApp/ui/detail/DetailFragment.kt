package com.rifqi.myPokeDexApp.ui.detail

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.rifqi.myPokeDexApp.R
import com.rifqi.myPokeDexApp.base.BaseFragment
import com.rifqi.myPokeDexApp.databinding.FragmentDetailBinding
import com.rifqi.myPokeDexApp.ui.MainActivity
import com.rifqi.myPokeDexApp.util.sealedclass.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : BaseFragment<DetailViewModel, FragmentDetailBinding>(
    DetailViewModel::class.java,
    R.layout.fragment_detail
) {
    companion object {
        fun newInstance(
            name: String,
            url : String
        ): DetailFragment {
            val fragment = DetailFragment()
            val bundle = Bundle()
            bundle.apply {
                putString(NAME, name)
                putString(URL, url)
            }
            fragment.arguments = bundle
            return fragment
        }

        private const val NAME = "name"
        private const val URL = "url"
    }

    private val detailViewModel: DetailViewModel by viewModel()
    override fun getVM(): DetailViewModel = detailViewModel

    private var baseActivity: MainActivity? = null
    private var name = ""
    private var url = ""
    private var id = 0
    private var type = ""
    private var height = 0
    private var weight = 0
    private var pokemonName = ""
    private var pokemonImage = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        baseActivity = (requireActivity() as MainActivity)
        arguments?.apply {
            name = getString(NAME) ?: ""
            url = getString(URL) ?: ""
        }
    }

    override fun onResume() {
        super.onResume()
        baseViewModel.getPokemonDetail(name)
        baseViewModel.checkPokemon(name)
    }

    override fun setListener() {
        with(binding) {
            super.setListener()
            baseViewModel.pokemonDetailList.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Loading -> {
                        setLoading(true)
                    }

                    is Resource.Success -> {
                        setLoading(false)
                        type = it.data.types?.get(0)?.type?.name ?: ""
                        height = it.data.height?.times(10) ?: 0
                        weight = it.data.weight?.div(10) ?: 0
                        pokemonName = it.data.name ?: ""
                        id = it.data.id ?: 0
                        pokemonImage = it.data.sprites?.frontDefault ?: ""
                        setupView()
                    }

                    is Resource.Error -> {
                        setLoading(false)
                        showToast("something went wrong")
                    }

                    else -> {}
                }
            }

            ivPokemonBall.setOnClickListener {
                val randomCatch = (1..2).random()
                if (randomCatch == 1) {
                    pokemonName.let { name -> baseViewModel.addToMyPokemonList(id, name, url) }
                    ivPokemonBall.apply {
                        this.setImageDrawable(
                            ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.ic_pokemon_ball_disable
                            )
                        )
                        this.isClickable = false
                        this.isEnabled = false
                    }
                    showToast("congratulations, you caught it")
                } else {
                    ivPokemonBall.apply {
                        this.setImageDrawable(
                            ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.ic_pokemon_ball
                            )
                        )
                        this.isClickable = true
                        this.isEnabled = true

                    }
                    showToast("Sorry, fail catch the pokemon, try again")
                }
            }

        }
    }

    private fun setupView() {
        binding.apply {
            Glide.with(requireContext())
                .load(pokemonImage)
                .into(ivPokemon)
            cvPokemonDetail.isVisible = true
            tvPokemonName.text = pokemonName
            tvType.text = "Power : ${type}"
            tvHeight.text = "$height cm"
            tvWeight.text = "$weight kg"

            if (baseViewModel.pokemonEntity?.name.equals(name, true)) {
                ivPokemonBall.apply {
                    this.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_pokemon_ball_disable
                        )
                    )
                    this.isClickable = false
                    this.isEnabled = false
                }
                tvCatch.text = "You got it :D"

            } else {
                ivPokemonBall.apply {
                    this.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_pokemon_ball
                        )
                    )
                    this.isClickable = true
                    this.isEnabled = true
                }
                tvCatch.text = "Catch !"
            }
        }
    }

    private fun setLoading(isLoading: Boolean){
        with(binding){
            pbLoading.isVisible = isLoading
            if(isLoading) {
                requireActivity().runOnUiThread {
                    requireActivity().window?.setFlags(
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                    )
                }
            } else {
                requireActivity().runOnUiThread {
                    requireActivity().window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                }
            }
        }
    }

}