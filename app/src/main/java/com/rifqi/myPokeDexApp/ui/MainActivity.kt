package com.rifqi.myPokeDexApp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.rifqi.core.model.Home
import com.rifqi.myPokeDexApp.R
import com.rifqi.myPokeDexApp.base.BaseActivity
import com.rifqi.myPokeDexApp.databinding.ActivityMainBinding
import com.rifqi.myPokeDexApp.ui.detail.DetailFragment
import com.rifqi.myPokeDexApp.ui.home.HomeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(
    MainViewModel::class.java,
    R.layout.activity_main
){

    companion object {
        fun newInstance(
            context: Context,
            name: String,
            flag: String
        ) {
            Intent(context, MainActivity::class.java).also {
                it.putExtra(NAME, name)
                it.putExtra(FLAG, flag)
                context.startActivity(it)
            }
        }

        private const val FLAG = "flag"
        private const val NAME = "name"
        private const val DETAIL = "detail"
        private const val OWN = "own"
    }

    private val mainViewModel : MainViewModel by viewModel()
    private var flag = ""
    private var name = ""
    override fun getVM(): MainViewModel = mainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        intent?.apply {
            name = getStringExtra(NAME) ?: ""
            flag = getStringExtra(FLAG)  ?: ""
        }

        setupContent(flag, name)

    }

    private fun setupContent(type : String, name : String){
        if (type.equals(DETAIL , true)) {
            replaceFragment(DetailFragment.newInstance(name = name))
        } else if (type.equals(OWN , true)) {

        } else {
            replaceFragment(HomeFragment.newInstance())
        }
    }
}