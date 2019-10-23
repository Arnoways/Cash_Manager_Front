package com.example.cashmanagerfront

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import com.example.cashmanagerfront.data.ProductDataSource
import com.example.cashmanagerfront.data.model.Product

import android.util.Log

class ProductList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        var productList: MutableList<Product> = ProductDataSource().loadProducts()
        Log.d("DEBUG", productList.toString())
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
            tvname.setText(productList[index].name.toString())
            tvprice.setText(productList[index].price.toString())
            // add it to our row
            row.addView(tvname, 0)
            row.addView(tvprice,1)
            // add row to our table
            tableLayout.addView(row, index)
            index++
        }

    }
}
