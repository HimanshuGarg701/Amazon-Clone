package com.example.amazonn.AmazonnProducts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.amazonn.ShoppingCartProducts.CartProductDao

class ProductDescriptionViewModel(val cartDao : CartProductDao, application: Application) : AndroidViewModel(application){

}