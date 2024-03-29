package com.example.cashmanagerfront.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cashmanagerfront.data.ProductDataSource
import com.example.cashmanagerfront.models.Product

import android.widget.*
import androidx.core.view.setPadding
import android.widget.LinearLayout
import com.example.cashmanagerfront.R
import com.example.cashmanagerfront.objects.Cart
import com.example.cashmanagerfront.objects.api.Api


class ProductList : AppCompatActivity() {

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        // add onclick on go to cart button
        var buttonGoToCart = findViewById<Button>(R.id.productListButtonCheckCart)
        buttonGoToCart.setOnClickListener {
            startActivity(Intent(this, ResumeCart::class.java))
        }

        var productList = Api.getAllProducts()
        var tableView: LinearLayout = findViewById(R.id.productListScrollLinearLayout)

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
            val quantityView = TextView(this)
            val addButton = Button(this)
            val textLayout = LinearLayout(this)

            // add event listener to buttons
            addButton.setOnClickListener {
                var ret = Cart.add(product)
                // call api add product to cart


                if (ret == true) {
                    quantityView.setText((quantityView.getText().toString().toInt() + 1).toString())
                    Toast.makeText(
                        this,
                        "Product added to cart",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            // set the text values
            nameView.setText(product.name.toString().replace("\"", ""))
            quantityView.setText(Cart.countProducts(product).toString())
            priceView.setText("Price : %.2f $".format(product.price))

            textLayout.addView(nameView)
            textLayout.addView(priceView)

            // set buttons
            addButton.setText("+")

            textLayout.orientation = LinearLayout.VERTICAL

            // set the components id
            nameView.id = Math.random().toInt()
            priceView.id = Math.random().toInt()
            quantityView.id = Math.random().toInt()
            addButton.id = Math.random().toInt()

            // stylish text view
            nameView.setTextAppearance(R.style.textViewStyle)
            priceView.setTextAppearance(R.style.textViewStyle)
            quantityView.setTextAppearance(R.style.textViewStyle)
            nameView.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM)
            priceView.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM)
            quantityView.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM)

            // set the layout params
            textLayout.setLayoutParams(LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                0.75F
            ))
            quantityView.setLayoutParams(LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1F
            ))

            // add it to our row
            row.addView(textLayout)
            row.addView(quantityView)
            row.addView(addButton)

            // add row to our table
            view.addView(row)
            index++
        }
    }
}
