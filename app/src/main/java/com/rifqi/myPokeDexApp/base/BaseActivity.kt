package com.rifqi.myPokeDexApp.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.rifqi.myPokeDexApp.R


abstract class BaseActivity<VM : ViewModel, DB: ViewDataBinding>(private val vm: Class<VM>, private val layout: Int) : AppCompatActivity(){

    protected lateinit var baseViewModel : VM
    protected lateinit var binding : DB
    private lateinit var root : ViewGroup
    private lateinit var loader : View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseViewModel = getVM()
        binding = DataBindingUtil.setContentView(this, layout)
        binding.lifecycleOwner = this

        // Loader
        root = findViewById(android.R.id.content)
        loader = LayoutInflater.from(this).inflate(R.layout.loader, null, false)

        setListener()
    }

    abstract fun getVM() : VM

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return super.dispatchTouchEvent(ev)
    }

    protected open fun setListener(){
        (baseViewModel as BaseViewModel).loading.observe(this, Observer {
                status ->
            when(status){
                BaseViewModel.SHOW_LOADING -> loading(true)
                BaseViewModel.REMOVE_LOADING -> loading(false)
            }
        })
    }

    private fun loading(isShow : Boolean){
        root.removeView(loader)
        if(isShow){
            root.addView(loader)
        }
    }

    fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()

        transaction.replace(R.id.container, fragment, fragment.javaClass.toString())
        transaction.addToBackStack(fragment.javaClass.toString())
        transaction.commit()
    }

    fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment, fragment.javaClass.toString())
        transaction.commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        loading(false)
    }
}