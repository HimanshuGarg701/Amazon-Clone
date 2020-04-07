package com.example.amazonn


import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.amazonn.databinding.ActivityProductDescriptionBinding
import com.squareup.picasso.Picasso


class ProductDescription : AppCompatActivity() {

    private lateinit var binding : ActivityProductDescriptionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_description)

        val product = intent.getParcelableExtra<Product>("PRODUCT")
        setValues(product!!)

        binding.buyButton.setOnClickListener {
            Toast.makeText(this, "Product added to Cart", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ShoppingCart::class.java)
            intent.putExtra("PRODUCT", product)
            //startActivity(intent)
        }
    }

    private fun setValues(product : Product){
        binding.productDescriptionName.text = product.name
        binding.productDescriptionPrice.text = product.price
        binding.productDescriptionData.text = product.description
        val imageView = binding.productDescriptionImage
        try {
            if (!product.imageURL.equals(""))
                Picasso.get().load(product.imageURL).into(imageView)
            else {
                binding.productDescriptionImage.setImageResource(R.drawable.no_pic_available)
            }
        }catch(e:Exception){
            binding.productDescriptionImage.setImageResource(R.drawable.no_pic_available)
        }
    }
}



