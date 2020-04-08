package com.example.amazonn

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel

class ProductDescriptionViewModel(val cartDao : CartProductDao, application: Application) : AndroidViewModel(application){

}