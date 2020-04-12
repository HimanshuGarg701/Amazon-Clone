package com.example.amazonn

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel

class ShoppingCartViewModel(
    private val cartDao: CartProductDao,
    application: Application) : AndroidViewModel(application) {

    val products = cartDao.getAllProducts()

    val total = cartDao.getPrice()

}