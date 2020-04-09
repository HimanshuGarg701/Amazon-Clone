package com.example.amazonn


import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.amazonn.databinding.ActivityProductDescriptionBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*


class ProductDescription : AppCompatActivity() {

    private lateinit var binding : ActivityProductDescriptionBinding
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    lateinit var cartDao : CartProductDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_description)

        val product = intent.getParcelableExtra<Product>("PRODUCT")
        setValues(product!!)

        binding.buyButton.setOnClickListener {
            Toast.makeText(this, "Product added to Cart", Toast.LENGTH_SHORT).show()
            addProduct(product)
        }

        binding.addReviewButton.setOnClickListener {
            val intent = Intent(this, AddReview::class.java)
            startActivity(intent)
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

    private fun addProduct(product : Product){
        val thisApplication = requireNotNull(this).application
        uiScope.launch {
            insert(thisApplication, product)
        }
    }

    private suspend fun insert(thisApplication : Application, product:Product){
        withContext(Dispatchers.IO) {
            Log.d("Leaving","Going to get the instance")
            cartDao = ShoppingCartDatabase.getInstance(thisApplication).cartProductDao
            Log.d("Leaving", "Coming back with the instance")
            if(cartDao.getProduct(product.id)!=null){
                // Do Nothing
            }else {
                cartDao.insert(product)
            }
        }
    }
}



