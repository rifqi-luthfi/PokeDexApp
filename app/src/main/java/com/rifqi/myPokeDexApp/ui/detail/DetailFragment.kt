package com.rifqi.myPokeDexApp.ui.detail

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.rifqi.core.model.DetailPokemonResponse
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
        ): DetailFragment {
            val fragment = DetailFragment()
            val bundle = Bundle()
            bundle.apply {
                putString(NAME, name)
            }
            fragment.arguments = bundle
            return fragment
        }

        private const val NAME = "name"
    }

    private val detailViewModel: DetailViewModel by viewModel()
    override fun getVM(): DetailViewModel = detailViewModel

    private var baseActivity: MainActivity? = null
    private var name = ""
    private var url = ""
    private var id = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        baseActivity = (requireActivity() as MainActivity)
        arguments?.apply {
            name = getString(NAME) ?: ""
        }
    }

    override fun onResume() {
        super.onResume()
        baseViewModel.getPokemonDetail(name)
    }

    override fun setListener() {
        with(binding) {
            super.setListener()
            baseViewModel.pokemonDetailList.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Loading -> {}

                    is Resource.Success -> {
                        setupView(it.data)
                    }

                    is Resource.Error -> {
                        showToast("something went wrong")
                    }

                    else -> {}
                }
            }
        }
    }

    private fun setupView(data: DetailPokemonResponse) {
        binding.apply {
            Glide.with(requireContext())
                .load(data.sprites?.frontDefault)
                .into(ivPokemon)
            val height = data.height?.times(10)
            val weight = data.weight?.div(10)

            tvPokemonName.text = data.name
            tvType.text = "Power : ${data.types?.get(0)?.type?.name}"
            tvHeight.text = "$height cm"
            tvWeight.text = "$weight kg"
            url = data.sprites?.frontDefault.toString()
            id = data.id ?: 0
        }
    }


}