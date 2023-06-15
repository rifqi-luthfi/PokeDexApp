package com.rifqi.myPokeDexApp.ui.mypokemon

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.rifqi.core.data.local.entity.PokemonEntity
import com.rifqi.core.model.PokemonResponse
import com.rifqi.myPokeDexApp.R
import com.rifqi.myPokeDexApp.base.BaseFragment
import com.rifqi.myPokeDexApp.databinding.FragmentMyPokemonBinding
import com.rifqi.myPokeDexApp.ui.MainActivity
import com.rifqi.myPokeDexApp.ui.mypokemon.adapter.MyPokemonAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyPokemonFragment : BaseFragment<MyPokemonViewModel, FragmentMyPokemonBinding>(
    MyPokemonViewModel::class.java,
    R.layout.fragment_my_pokemon
) {

    companion object {
        fun newInstance(): MyPokemonFragment {
            return MyPokemonFragment()
        }
    }

    private val myPokemonViewModel: MyPokemonViewModel by viewModel()
    override fun getVM(): MyPokemonViewModel = myPokemonViewModel

    private var baseActivity: MainActivity? = null
    private var myPokemonAdapter: MyPokemonAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        baseActivity = (requireActivity() as MainActivity)
        setAdapter()
    }

    private fun setAdapter() {
        binding.apply {
            myPokemonAdapter = MyPokemonAdapter().apply {
                onItemClickCallback = {
                    MainActivity.newInstance(context = requireContext(),name = it.name,  flag = "detail", url = "")
                }
                onItemDeleteCallback = {
                    AlertDialog.Builder(requireContext())
                        .setTitle("Delete")
                        .setMessage("Are You Sure")
                        .setPositiveButton("Yes"){
                            dialog,_->
                            baseViewModel.removeFromMyList(it.name)
                            baseViewModel.getMyPokemonList()?.observe(viewLifecycleOwner) {
                                if (it != null) {
                                    val myPokemonList = mapList(it)
                                    myPokemonAdapter?.updateData(myPokemonList)
                                }
                            }
                            showToast("Delete Success")
                            dialog.dismiss()
                        }
                        .setNegativeButton("No"){
                                dialog,_->
                            dialog.dismiss()
                        }
                        .show()
                }
            }
            rvMyPokemon.layoutManager = LinearLayoutManager(requireContext())
            rvMyPokemon.adapter = myPokemonAdapter
        }
    }

    override fun setListener() {
        with(binding) {
            super.setListener()
            baseViewModel.getMyPokemonList()?.observe(this@MyPokemonFragment) {
                if (it != null) {
                    val myPokemonList = mapList(it)
                    myPokemonAdapter?.updateData(myPokemonList)
                }
            }
        }
    }

    private fun mapList(pokemon: List<PokemonEntity>): ArrayList<PokemonResponse.PokemonItem> {
        val listUser = ArrayList<PokemonResponse.PokemonItem>()
        for (poke in pokemon) {
            val userMapped = PokemonResponse.PokemonItem (
                poke.name,
                poke.url
            )
            listUser.add(userMapped)
        }
        return listUser
    }

}