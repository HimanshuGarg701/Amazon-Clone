package com.example.amazonn

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.amazonn.databinding.ActivityReviewsBinding
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.*
import java.util.*


// Need to change the UI for Adding review
// Can add Material design
class AddReview : AppCompatActivity() {

    private lateinit var binding : ActivityReviewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_reviews
        )

        val product = intent.getParcelableExtra<Product>("PRODUCT")
        binding.submitReview.setOnClickListener {
            val heading = binding.reviewHeading.text.toString()
            val reviewData = binding.reviewData.text.toString()
            val reviewId = UUID.randomUUID().toString()

            val review = Review(
                product.id,
                reviewId,
                heading,
                reviewData
            )

            val ref = FirebaseDatabase.getInstance().getReference("/reviews/$reviewId")

            ref.setValue(review)
                .addOnSuccessListener {
                    Log.d("ReviewDatabase", "Successfully added review to database")
                }
                .addOnFailureListener {
                    Log.d("ReviewDatabase", "Failed to add review to database")
                }

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }


}