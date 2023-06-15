package com.rifqi.myPokeDexApp.ui

import android.os.Bundle
import com.rifqi.myPokeDexApp.R
import com.rifqi.myPokeDexApp.base.BaseActivity
import com.rifqi.myPokeDexApp.databinding.ActivityMainBinding
import com.rifqi.myPokeDexApp.ui.home.HomeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(
    MainViewModel::class.java,
    R.layout.activity_main
){
    companion object{
        const val MAIN_ACTIVITY = "main_activity"
    }
    private val mainViewModel : MainViewModel by viewModel()
    override fun getVM(): MainViewModel = mainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, HomeFragment(), MAIN_ACTIVITY)
            .commit()

    }
}