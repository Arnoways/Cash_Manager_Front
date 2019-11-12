package com.example.cashmanagerfront

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TableLayout
import com.example.cashmanagerfront.R
import com.example.cashmanagerfront.data.manager.Cart

class ResumeCart : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resume_cart)

        // add onclick on go to cart button
        var buttonBackToProducts = findViewById<Button>(R.id.buttonBack)
        buttonBackToProducts.setOnClickListener {
            startActivity(Intent(this, ProductList::class.java))
        }


        var tableLayout = findViewById<TableLayout>(R.id.tableLayoutBillDetail)

        var orders = Cart.orders()

        System.out.print(orders.toString())
    }
}
