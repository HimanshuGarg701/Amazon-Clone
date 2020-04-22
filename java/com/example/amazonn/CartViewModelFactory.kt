package com.example.amazonn

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class CartViewModelFactory(private val cartDao : CartProductDao,
                           private val application : Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ShoppingCartViewModel::class.java)){
            return ShoppingCartViewModel(
                cartDao,
                application
            ) as T
        }
        throw IllegalArgumentException("View Model cannot be created")
    }
}