package com.example.cashmanagerfront

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import com.example.cashmanagerfront.data.ProductDataSource
import com.example.cashmanagerfront.data.model.Product

import android.util.Log
import android.util.TypedValue
import android.widget.Button
import androidx.core.view.setPadding

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
        System.out.println("Product list : " + productList.toString())
        // get Table Layout
        var tableLayout: TableLayout = findViewById(R.id.tableLayout)

        var index = 0
        while(index < productList.size) {
            // create a row
            var row: TableRow = TableRow(this)
            // parameters for the row
            val lp = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT)
            row.setLayoutParams(lp)
            // create the textviews
            val tvname = TextView(this)
            val tvprice = TextView(this)
            // set the text values
            tvname.setText(productList[index].name)
            tvprice.setText(productList[index].price.toString())
            // stylish text view
            tvname.setTextAppearance(R.style.textViewStyle)
            tvprice.setTextAppearance(R.style.textViewStyle)
            // add it to our row
            row.addView(tvname, 0)
            row.addView(tvprice,1)
            // add row to our table
            tableLayout.addView(row, index+1)
            index++
        }

    }
}
