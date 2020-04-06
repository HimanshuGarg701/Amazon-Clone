package com.example.amazonn

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.amazonn.databinding.ProductAppearanceBinding

class ProductListAdapter(private val products : List<Product>) : RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {

    //val products = ArrayList<Product>()
    override fun getItemCount(): Int {
        return products.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ProductAppearanceBinding.inflate(layoutInflater, parent,false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
        holder.product = product
    }


    class ProductViewHolder(private val binding: ProductAppearanceBinding,
                            var product : Product?=null) : RecyclerView.ViewHolder(binding.root){
        init{
            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, ProductDescription::class.java)
                intent.putExtra("PRODUCT", product)
                binding.root.context.startActivity(intent)
            }
        }

         fun bind(product : Product){
             binding.productName.text = product.name
             binding.productPrice.text = product.price
         }
    }
}
