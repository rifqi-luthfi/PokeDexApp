package com.rifqi.myPokeDexApp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.rifqi.myPokeDexApp.R
import com.rifqi.myPokeDexApp.base.BaseActivity
import com.rifqi.myPokeDexApp.databinding.ActivityMainBinding
import com.rifqi.myPokeDexApp.ui.detail.DetailFragment
import com.rifqi.myPokeDexApp.ui.home.HomeFragment
import com.rifqi.myPokeDexApp.ui.mypokemon.MyPokemonFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(
    MainViewModel::class.java,
    R.layout.activity_main
){

    companion object {
        fun newInstance(
            context: Context,
            name: String,
            flag: String,
            url: String
        ) {
            Intent(context, MainActivity::class.java).also {
                it.putExtra(NAME, name)
                it.putExtra(FLAG, flag)
                it.putExtra(URL, url)
                context.startActivity(it)
            }
        }

        private const val FLAG = "flag"
        private const val NAME = "name"
        private const val DETAIL = "detail"
        private const val OWN = "own"
        private const val URL = "url"
    }

    private val mainViewModel : MainViewModel by viewModel()
    private var flag = ""
    private var name = ""
    private var url = ""
    override fun getVM(): MainViewModel = mainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        intent?.apply {
            name = getStringExtra(NAME) ?: ""
            flag = getStringExtra(FLAG)  ?: ""
            url = getStringExtra(URL) ?: ""
        }

        setupContent(flag, name)

    }

    private fun setupContent(type : String, name : String){
        if (type.equals(DETAIL , true)) {
            replaceFragment(DetailFragment.newInstance(name = name, url = url))
        } else if (type.equals(OWN , true)) {
            replaceFragment(MyPokemonFragment.newInstance())
        } else {
            replaceFragment(HomeFragment.newInstance())
        }
    }
}