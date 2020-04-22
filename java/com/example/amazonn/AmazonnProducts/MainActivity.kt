package com.example.amazonn.AmazonnProducts

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.amazonn.R
import com.example.amazonn.Reviews.ReviewAdapter
import com.example.amazonn.ShoppingCartProducts.ShoppingCart
import com.example.amazonn.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_product_description.*
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.IO +job)
    private lateinit var adapter: ProductListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )

        fetchProducts()
        binding.recyclerProducts.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        //val products = formulateMap()
        //var productTwo =ArrayList<Product>()
        //productTwo.clear()
        //productTwo.addAll(products)
        //adapter = ProductListAdapter(products, productTwo)
        //binding.recyclerProducts.adapter = adapter

    }

    private fun fetchProducts(){
        val productsList = ArrayList<Product>()
        val productClone = ArrayList<Product>()
        val ref = FirebaseDatabase.getInstance().getReference("/products")
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(products: DataSnapshot) {
                try {
                    products.children.forEach {
                        Log.d("FirebaseErrorHere", it.getValue(Product::class.java).toString())
                        productsList.add(it.getValue(Product::class.java)!!)
                    }
                }catch(e : Exception){
                    Log.d("FirebaseError", "$e.message")
                }
                productClone.clear()
                productClone.addAll(productsList)
                binding.recyclerProducts.adapter =
                    ProductListAdapter(
                        productsList,
                        productClone
                    )
            }

        })
    }

//    private fun loadJSONFromAsset(): String {
//        val json: String
//        json = try {
//            val stream: InputStream = assets.open("products.json")
//            val size: Int = stream.available()
//            val buffer = ByteArray(size)
//            stream.read(buffer)
//            stream.close()
//            String(buffer, Charsets.UTF_8)
//        } catch (ex: IOException) {
//            ex.printStackTrace()
//            return "null"
//        }
//        return json
//    }

//    private fun formulateMap() : ArrayList<Product>{
//        val list = arrayListOf<Product>()
//        try {
//            val obj = JSONObject(loadJSONFromAsset())
//            val jsonObjects = obj.getJSONArray("products")
//
//            for (i in 0 until jsonObjects.length()) {
//                val products = jsonObjects.getJSONObject(i)
//
//                val id = products.getInt("id")
//                val name = products.getString("name")
//                val price = products.getDouble("price")
//                val quantity = products.getString("quantity")
//                val imageUrl = products.getString("imageUrl")
//                val description = products.getString("description")
//
//                val ref = FirebaseDatabase.getInstance().getReference("/products/$id")
//
//                val product = Product(id, name, price, quantity, imageUrl, description, "")
//
//                ref.setValue(product)
//                    .addOnSuccessListener {
//                        Log.d("DatabaseStorage", "Stored the user to firebase")
//                    }
//                    .addOnFailureListener {
//                        Log.d("DatabaseStorage", "Failed to store the user")
//                    }
//                val application = requireNotNull(this).application
//                uiScope.launch {
//                    withContext(Dispatchers.IO){
//                        val reviewDao = ReviewDatabase.getInstance(application).reviewDao
//                        val reviews = reviewDao.getAllReviews(id)
//                        if(reviews!=null || reviews!=""){
//                            product.reviews = reviews
//                        }
//                        list.add(product)
//                    }
//                }
//                list.add(product)
//
//            }
//        } catch (e: JSONException) {
//            e.printStackTrace()
//        }
//        return list
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        try{
            when(item.itemId){
                R.id.shoppingCart ->{
                    val intent = Intent(this, ShoppingCart::class.java)
                    startActivity(intent)
                }
            }
        }catch(e : Exception){
            Log.d("FailedToLoadMenu", e.message)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.shopping_cart, menu)
        val menuItem = menu?.findItem(R.id.search)
        val searchView = menuItem!!.actionView as SearchView
        searchView.setOnQueryTextListener( object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    fun loadProductReviews(id : Int){
        try {
            val application = requireNotNull(this).application

                    val reviewsDao = ReviewDatabase.getInstance(application).reviewDao
                    val review = reviewsDao.getAllReviews(id)
                    try {
                        if (review != null && review.isNotEmpty()) {
                            val reviewList = TypeConvertor()
                                .stringToObject(review)
                            val adapter =
                                ReviewAdapter(
                                    reviewList!!
                                )
                            recyclerReviews.adapter = adapter
                        } else {
                            val reviewList = ArrayList<String>()
                            val adapter =
                                ReviewAdapter(
                                    reviewList
                                )
                            recyclerReviews.adapter = adapter
                        }
                    } catch (e: Exception) {
                        Log.d("ReviewError", e.message)
                    }
        }catch(e : Exception){
            Log.d("ErrorReviewing", e.message)
        }
    }
}
