package com.example.amazonn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.amazonn.databinding.ActivityProductDescriptionBinding
import kotlinx.android.synthetic.main.activity_product_description.*

class ProductDescription : AppCompatActivity() {

    private lateinit var binding : ActivityProductDescriptionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_product_description)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_description)
        val product = intent.getParcelableExtra<Product>("PRODUCT")

        setValues(product!!)
    }

    private fun setValues(product : Product){
        binding.productDescriptionName.text = product.name
        binding.productDescriptionPrice.text = product.price
    }
}



