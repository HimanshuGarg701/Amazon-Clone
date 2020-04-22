package com.example.amazonn

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.amazonn.CartProductDao

class ShoppingCartViewModel(
    private val cartDao: CartProductDao,
    application: Application) : AndroidViewModel(application) {

    val products = cartDao.getAllProducts()

    val total = cartDao.getPrice()

}