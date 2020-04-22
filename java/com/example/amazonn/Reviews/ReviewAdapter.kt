package com.example.amazonn.Reviews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amazonn.databinding.ReviewAppearanceBinding

class ReviewAdapter(private val reviews : ArrayList<String>) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ReviewAppearanceBinding.inflate(layoutInflater, parent, false)
        return ReviewViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]
        holder.bind(review)
    }

    class ReviewViewHolder(val binding: ReviewAppearanceBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(review : String){
            binding.reviewAppearanceData.text = review.toString()
        }

    }
}