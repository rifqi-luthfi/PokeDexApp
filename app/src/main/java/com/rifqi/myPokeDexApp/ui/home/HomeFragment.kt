package com.rifqi.myPokeDexApp.ui.home

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.rifqi.myPokeDexApp.R
import com.rifqi.myPokeDexApp.base.BaseFragment
import com.rifqi.myPokeDexApp.databinding.FragmentHomeBinding
import com.rifqi.myPokeDexApp.ui.MainActivity
import com.rifqi.myPokeDexApp.ui.home.adapter.HomeAdapter
import com.rifqi.myPokeDexApp.util.sealedclass.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(
    HomeViewModel::class.java,
    R.layout.fragment_home
) {
    private val homeViewModel: HomeViewModel by viewModel()
    override fun getVM(): HomeViewModel = homeViewModel

    private var baseActivity: MainActivity? = null

    private var homeAdapter: HomeAdapter? = null

    override fun onResume() {
        super.onResume()
        baseViewModel.getPokemonList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        baseActivity = (requireActivity() as MainActivity)
        setAdapter()
    }

    private fun setAdapter() {
        binding.apply {
            homeAdapter = HomeAdapter().apply {
                onItemClickCallback = {
//                    CodeGeneratorActivity.newInstance(
//                        context = requireContext(),
//                        orderId = it.refNum
//                    )
                }
            }
            rvPokemon.layoutManager = GridLayoutManager(requireContext(), 2)
            rvPokemon.adapter = homeAdapter
        }
    }

    override fun setListener() {
        with(binding) {
            super.setListener()
            baseViewModel.pokemonList.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        val pokemons = it.data.results
                        homeAdapter?.updateData(pokemons = pokemons)
                    }

                    is Resource.Error -> {
                        showToast("something went wrong")
                    }

                    else -> {}
                }
            }
        }
    }


}