package com.example.fluperdemo.view

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fluperdemo.R
import com.example.fluperdemo.adapter.AllProductsListAdapter
import com.example.fluperdemo.viewmodel.ProductViewModel

class ShowProductActivity : AppCompatActivity() {

    private lateinit var rvProducts: RecyclerView
    private lateinit var rvLayoutManager: RecyclerView.LayoutManager
    private lateinit var productViewModel: ProductViewModel
    private lateinit var context: Context
    private var width: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_product)
        rvProducts = findViewById(R.id.rvProducts)
        context = this
        width = resources.displayMetrics.widthPixels

        rvLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvProducts.layoutManager = rvLayoutManager
        rvProducts.setHasFixedSize(true);
        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        productViewModel.allProducts.observe(this, Observer { products ->

            val productAdapter = AllProductsListAdapter(products, width, context)
            rvProducts.adapter = productAdapter
        })

    }


}