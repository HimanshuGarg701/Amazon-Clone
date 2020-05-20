package com.example.amazonn

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.amazonn.databinding.ActivityProductDescriptionBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*

class ProductDescription : AppCompatActivity() {

    private lateinit var binding : ActivityProductDescriptionBinding
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private lateinit var cartDao : CartProductDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_product_description
        )

        val product = intent.getParcelableExtra<Product>("PRODUCT")
        setValues(product!!)
        supportActionBar!!.title = product.name
        val deleteProduct = intent.getParcelableExtra<Product>("DELETE_PRODUCT")

        binding.buyButton.setOnClickListener {
            Toast.makeText(this, "Product added to Cart", Toast.LENGTH_SHORT).show()
            addProducttoDatabase(product)
        }

        deleteProductFromDatabase(deleteProduct)

        loadReviews(product)

        binding.addReviewButton.setOnClickListener {
            val intent = Intent(this, AddReview::class.java)
            intent.putExtra("PRODUCT", product)
            startActivity(intent)
        }
    }

    internal fun setValues(product : Product) : Boolean{
        binding.productDescriptionName.text = product.name
        binding.productDescriptionPrice.text = "\$ ${product.price}"
        binding.productDescriptionData.text = product.description
        val imageView = binding.productDescriptionImage
        try {
            if (!product.imageURL.equals(""))
                Picasso.get().load(product.imageURL).into(imageView)
            else {
                binding.productDescriptionImage.setImageResource(R.drawable.no_pic_available)
            }
            return true
        }catch(e:Exception){
            binding.productDescriptionImage.setImageResource(R.drawable.no_pic_available)
            return false
        }
    }

    private fun addProducttoDatabase(product : Product) : Boolean{
        try {
            val thisApplication = requireNotNull(this).application
            uiScope.launch {
                insert(thisApplication, product)
            }
            return true
        }catch(e : Exception){
            return false
        }
    }

    private suspend fun insert(thisApplication : Application, product: Product){
        withContext(Dispatchers.IO) {
            cartDao = ShoppingCartDatabase.getInstance(thisApplication).cartProductDao
            try{
                if(cartDao.getProduct(product.id)!=null){
                    // Do Nothing
                }else {
                    cartDao.insert(product)
                }
            }catch(e : Exception){
                Log.d("BuyError", e.message)
            }
        }
    }

    private fun deleteProductFromDatabase(product : Product?){
        val thisApplication = requireNotNull(this).application
        uiScope.launch {
            delete(thisApplication, product)
        }
    }

    private suspend fun delete(thisApplication : Application, product : Product?){
        withContext(Dispatchers.IO){
            cartDao = ShoppingCartDatabase.getInstance(thisApplication).cartProductDao
            if(product!=null){
                cartDao.delete(product)
            }
        }
    }

     fun loadReviews(product : Product) : Boolean{
        val ref = FirebaseDatabase.getInstance().getReference("/reviews")
        val reviewsList = ArrayList<Review>()
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(reviews: DataSnapshot) {
                try {
                    reviews.children.forEach {
                        if (product.id == it.getValue(Review::class.java)!!.productId) {
                            reviewsList.add(it.getValue(Review::class.java)!!)
                        }
                    }
                    binding.recyclerReviews.adapter = ReviewAdapter(reviewsList)

                    binding.recyclerReviews.addItemDecoration(DividerItemDecoration(binding.root.context, DividerItemDecoration.VERTICAL))
                }catch(e : Exception){
                    Log.d("ReviewFail", e.message)
                }
            }
        })
        return true
    }

    fun addNums(x : Int, y: Int) : Int{
        return x+y
    }
}





