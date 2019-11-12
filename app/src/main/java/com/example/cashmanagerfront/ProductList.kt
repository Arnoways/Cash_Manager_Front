package com.example.cashmanagerfront

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cashmanagerfront.data.ProductDataSource
import com.example.cashmanagerfront.data.model.Product

import android.util.Log
import android.util.TypedValue
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.setPadding
import android.view.ViewGroup.LayoutParams.FILL_PARENT
import android.widget.ScrollView
import android.widget.LinearLayout
import com.example.cashmanagerfront.data.manager.Cart


class ProductList : AppCompatActivity() {

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        // add onclick on go to cart button
        var buttonGoToCart = findViewById<Button>(R.id.button_check_cart)
        buttonGoToCart.setOnClickListener {
            startActivity(Intent(this, ResumeCart::class.java))
        }

        var productList: MutableList<Product> = ProductDataSource(this).loadProducts()
        // get Table Layout
        var tableView: LinearLayout = findViewById(R.id.scrollLinearLayout1)

        generateTable(productList, tableView)
    }

    @SuppressLint("NewApi")
    /*
        Generate the product list with actions to add/remove a product to the cart
     */
    fun generateTable(products: MutableList<Product>, view: LinearLayout) {
        var index = 0
        while(index < products.size) {
            val product = products[index]
            // create a row
            val row = LinearLayout(this)
            // parameters for the row
            val rowLayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            row.setLayoutParams(rowLayoutParams)
            row.setPadding(5)
            row.setHorizontalGravity(0)
            row.setVerticalGravity(1)

            if (index % 2 == 0) {
                row.setBackgroundResource(R.color.colorPrimaryPastel)
            } else {
                row.setBackgroundResource(R.color.colorSecondaryPastel)
            }

            // create the table row subcomponents
            val nameView = TextView(this)
            val priceView = TextView(this)
            val addButton = Button(this)
            val removeButton = Button(this)
            val buttonLayout = LinearLayout(this)

            // add event listener to buttons
            addButton.setOnClickListener {
                var ret = Cart.add(product)
                if (ret == true) {
                    Toast.makeText(
                        this,
                        "Product added to cart",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            removeButton.setOnClickListener {
                var ret = Cart.remove(product)
                if (ret == true) {
                    Toast.makeText(
                        this,
                        "Product removed from cart",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            // set buttons
            addButton.setText("+")
            removeButton.setText("-")
            buttonLayout.addView(addButton)
            buttonLayout.addView(removeButton)

            addButton.id = Math.random().toInt()
            removeButton.id = Math.random().toInt()

            buttonLayout.orientation = LinearLayout.VERTICAL

            // set the text values
            nameView.setText(products[index].name.toString())
            priceView.setText(products[index].price.toString())

            // stylish text view
            nameView.setTextAppearance(R.style.textViewStyle)
            priceView.setTextAppearance(R.style.textViewStyle)
            nameView.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM)
            priceView.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM)

            // set the layout params
            nameView.setLayoutParams(LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                0.75F
            ))
            priceView.setLayoutParams(LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1F
            ))
            buttonLayout.setLayoutParams(LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1F
            ))

            // add it to our row
            row.addView(nameView)
            row.addView(priceView)
            row.addView(buttonLayout)

            // add row to our table
            view.addView(row)
            index++
        }
    }

    fun addProductCart(product: Product) {

    }

    fun removeProductCart(product: Product) {

    }
}
