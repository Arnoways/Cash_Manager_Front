package com.example.cashmanagerfront

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.cashmanagerfront.R

class ResumeCart : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resume_cart)

        // add onclick on go to cart button
        var buttonBackToProducts = findViewById<Button>(R.id.buttonBack)
        buttonBackToProducts.setOnClickListener {
            startActivity(Intent(this, ProductList::class.java))
        }
    }
}
