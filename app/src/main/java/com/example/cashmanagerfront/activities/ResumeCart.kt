package com.example.cashmanagerfront.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.view.setPadding
import com.example.cashmanagerfront.R
import com.example.cashmanagerfront.objects.Cart
import kotlinx.android.synthetic.main.activity_resume_cart.*

class ResumeCart : AppCompatActivity() {

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resume_cart)

        // add onclick on go to cart button
        buttonBack.setOnClickListener {
            startActivity(Intent(this, ProductList::class.java))
        }

        // add onclick on empty cart button
        buttonEmpty.setOnClickListener{
            // empty cart
            Cart.empty()

            // remove orders in the view
            cartScrollLinearLayout.removeAllViewsInLayout()

            // set the total ht and total ttc
            numberTotalHt.setText("0.00")
            numberTotalTtc.setText("0.00")

            Toast.makeText(
                this,
                "Cart cleaned",
                Toast.LENGTH_SHORT
            ).show()
        }


        val orders = Cart.orders()

        var totalHt = 0.0

        System.out.print(orders.toString())

        for(order in orders) {
            val row = LinearLayout(this)
            val rowLayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            row.setLayoutParams(rowLayoutParams)
            row.setPadding(5)
            row.setHorizontalGravity(0)
            row.setVerticalGravity(1)

            val nameView = TextView(this)
            val quantityView = TextView(this)
            val priceView = TextView(this)

            nameView.setText(order.name.toString())
            quantityView.setText(order.quantity.toString())
            priceView.setText("%.2f".format(order.price))

            // stylish text view
            nameView.setTextAppearance(R.style.textViewStyle)
            quantityView.setTextAppearance(R.style.textViewStyle)
            priceView.setTextAppearance(R.style.textViewStyle)
            nameView.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM)
            quantityView.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM)
            priceView.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM)


            // set the layout params
            nameView.setLayoutParams(LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                0.5F
            ))
            quantityView.setLayoutParams(
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1F
                ))
            priceView.setLayoutParams(LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1F
            ))

            row.addView(nameView)
            row.addView(quantityView)
            row.addView(priceView)

            cartScrollLinearLayout.addView(row)

            totalHt += order.price

        }

        // add price ht to number view
        numberTotalHt.setText("%.2f".format(totalHt))
        // add price ttc to number view
        numberTotalTtc.setText("%.2f".format(totalHt * 1.20))
    }
}
