package com.example.amazonn

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amazonn.databinding.ShoppingCartItemBinding
import com.squareup.picasso.Picasso
import java.lang.Exception

class ShoppingCartAdapter(private val products : List<Product>) : RecyclerView.Adapter<ShoppingCartAdapter.ShoppingCartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingCartViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ShoppingCartItemBinding.inflate(layoutInflater, parent, false)
        return ShoppingCartViewHolder(
            binding
        )
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ShoppingCartViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
    }

    class ShoppingCartViewHolder(private val binding: ShoppingCartItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product : Product){
            binding.productName.text = product.name
            binding.productPrice.text = "\$ ${product.price}"
            if(!product.imageURL.equals(""))
                Picasso.get().load(product.imageURL).into(binding.productImage)
            else{
                binding.productImage.setImageResource(R.drawable.no_pic_available)
            }

            try {
                binding.removeButton.setOnClickListener {
                    val intent = Intent(binding.root.context, ProductDescription::class.java)
                    intent.putExtra("PRODUCT", product)
                    intent.putExtra("DELETE_PRODUCT", product)
                    binding.root.context.startActivity(intent)
                }
            }catch(e:Exception){
                Log.d("FailedNewActivity", e.message)
            }
        }
    }
}

