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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.amazonn.databinding.ActivityProductDescriptionBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_description.*
import kotlinx.coroutines.*


class ProductDescription : AppCompatActivity() {

    private lateinit var binding : ActivityProductDescriptionBinding
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private lateinit var cartDao : CartProductDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_description)

        val product = intent.getParcelableExtra<Product>("PRODUCT")
        setValues(product!!)

        binding.buyButton.setOnClickListener {
            Toast.makeText(this, "Product added to Cart", Toast.LENGTH_SHORT).show()
            addProducttoDatabase(product)
        }

        binding.addReviewButton.setOnClickListener {
            val intent = Intent(this, AddReview::class.java)
            startActivity(intent)
        }

        loadReviews()
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

    private fun addProducttoDatabase(product : Product){
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

    private fun loadReviews(){
        val application = requireNotNull(this).application
        val reviewsDao = ReviewDatabase.getInstance(application).reviewDao

        val reviewViewModelFactory = ReviewsViewModelFactory(reviewsDao, application)
        val reviewViewModel = ViewModelProviders.of(this, reviewViewModelFactory).get(ReviewsViewModel::class.java)

        reviewViewModel.reviews.observe(this, Observer{
            val reviewsList = it as ArrayList<Review>
            Log.d("ReviewsList", reviewsList.toString())
            val adapter = ReviewAdapter(reviewsList)
            recyclerReviews.adapter = adapter
        })
    }
}



