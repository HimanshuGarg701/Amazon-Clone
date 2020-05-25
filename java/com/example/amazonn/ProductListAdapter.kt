package com.example.amazonn

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.amazonn.databinding.ProductAppearanceBinding
import com.squareup.picasso.Picasso

class ProductListAdapter(private var products : ArrayList<Product>, private var listOfProducts:ArrayList<Product>) :
                                    RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>(), Filterable {


    override fun getItemCount(): Int {
        return products.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ProductAppearanceBinding.inflate(layoutInflater, parent,false)
        return ProductViewHolder(
            binding
        )
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
             binding.productPrice.text = "\$ ${product.price}"
             if(!product.imageURL.equals(""))
                 Picasso.get().load(product.imageURL).into(binding.productImage)
             else{
                 binding.productImage.setImageResource(R.drawable.no_pic_available)
             }
         }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterList = ArrayList<Product>()
                if (constraint.toString()=="" || constraint == null || constraint.length==0) {
                    filterList.addAll(listOfProducts)
                } else {
                    for (product in listOfProducts) {
                        if (product.name?.toLowerCase()!!
                                .contains(constraint.toString().toLowerCase())
                        ) {

                            filterList.add(product)
                        }
                    }
                }
                val filteredResults = FilterResults()
                filteredResults.values = filterList
                return filteredResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                products.clear()
                if (results?.values != null && results.values!="") {
                    products.addAll(results.values as Collection<Product>)
                    notifyDataSetChanged()
                }
            }
        }
    }
}



