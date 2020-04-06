package com.example.amazonn


import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.amazonn.databinding.ActivityProductDescriptionBinding
import com.squareup.picasso.Picasso


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
        val imageView =
            findViewById<ImageView>(R.id.productDescriptionImage)
        //val productImageView = (ImageView) findViewById(R.id.productImage)
        if(!product.imageURL.equals(""))
            Picasso.get().load(product.imageURL).into(imageView)
        else{
            binding.productDescriptionImage.setImageResource(R.drawable.no_pic_available)
        }
    }
}



