package com.example.amazonn

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.amazonn.databinding.ActivityMainBinding
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        val products = formulateMap()

        val adapter = ProductListAdapter(products)
        binding.recyclerProducts.adapter = adapter

    }

    private fun loadJSONFromAsset(): String {
        val json: String
        json = try {
            val stream: InputStream = assets.open("products.json")
            val size: Int = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return "null"
        }
        return json
    }

    private fun formulateMap() : List<Product>{
        val list = arrayListOf<Product>()
        try {
            val obj = JSONObject(loadJSONFromAsset())
            val jsonObjects = obj.getJSONArray("products")

            for (i in 0 until jsonObjects.length()) {
                val products = jsonObjects.getJSONObject(i)

                val id = products.getInt("id")
                val name = products.getString("name")
                val price = products.getDouble("price")
                val quantity = products.getString("quantity")
                val imageUrl = products.getString("imageUrl")
                val description = products.getString("description")

                val product = Product(id, name, price, quantity, imageUrl, description)
                list.add(product)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return list
    }

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
        return super.onCreateOptionsMenu(menu)
    }
}
