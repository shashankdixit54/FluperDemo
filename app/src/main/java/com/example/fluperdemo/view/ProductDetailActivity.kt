package com.example.fluperdemo.view

import android.app.Dialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.fluperdemo.Constant
import com.example.fluperdemo.R
import com.example.fluperdemo.model.Product
import com.example.fluperdemo.viewmodel.ProductViewModel
import java.io.File

class ProductDetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var productIdTv: TextView
    private lateinit var nameTv: TextView
    private lateinit var descriptionTv: TextView
    private lateinit var regularPriceTv: TextView
    private lateinit var salePriceTv: TextView
    private lateinit var productIv: ImageView
    private lateinit var updateBtn: Button
    private lateinit var deleteBtn: Button
    private lateinit var colorsTv: TextView
    private lateinit var storesTv: TextView
    private lateinit var productViewModel: ProductViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        productIdTv = findViewById<TextView>(R.id.tvID)
        nameTv = findViewById<TextView>(R.id.tvName)
        descriptionTv = findViewById<TextView>(R.id.tvDescription)
        regularPriceTv = findViewById<TextView>(R.id.tvRegularPrice)
        salePriceTv = findViewById<TextView>(R.id.tvSellingPrice)
        productIv = findViewById<ImageView>(R.id.ivProductDetail)
        colorsTv = findViewById<TextView>(R.id.tvColors)
        storesTv = findViewById<TextView>(R.id.tvStores)

        updateBtn = findViewById<Button>(R.id.btnUpdate)
        deleteBtn = findViewById<Button>(R.id.btnDelete)
        updateBtn.setOnClickListener(this)
        deleteBtn.setOnClickListener(this)
        productIv.setOnClickListener(this)

        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        // To retrieve object send in intent
        val product = intent.getSerializableExtra(Constant.sendObject) as Product
        if (null != product)
            setAllData(product)


    }

    private fun setAllData(product: Product) {

        productIdTv.text = product.id.toString()
        nameTv.text = product.name
        descriptionTv.text = product.description
        regularPriceTv.text = product.regularPrice.toString()
        salePriceTv.text = product.salePrice.toString()

        val colorsArr = product.colors
        val colorsString: StringBuffer = StringBuffer()
        for (i in 0 until colorsArr.size) {
            colorsString.append(colorsArr[i]).append("\n")
        }
        colorsTv.text = colorsString.toString()

        val storesArr = product.stores
        val storeString: StringBuffer = StringBuffer()
        for (i in 0 until storesArr.size) {
            storeString.append(storesArr[i].storeName).append("\n")
                .append(storesArr[i].storeAddress).append("\n\n")
        }
        storesTv.text = storeString.toString()

        val file = File(product.image)
        if (file.exists()) {
            val bitmap = BitmapFactory.decodeFile(file.absolutePath);
            productIv.setImageBitmap(bitmap)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            updateBtn.id -> {

                val intent = Intent(this, CreateProductActivity::class.java)
                val dataIntent = getIntent()
                dataIntent.putExtra(Constant.selection, Constant.updatePage)
                intent.putExtras(dataIntent)
                startActivity(intent)
                finish()

            }
            deleteBtn.id -> {
                val product = intent.getSerializableExtra(Constant.sendObject) as Product
                productViewModel.delete(product.id)
                Toast.makeText(
                    this@ProductDetailActivity,
                    getString(R.string.product_deleted),
                    Toast.LENGTH_LONG
                ).show()

                finish()

            }
            productIv.id -> {
                val product = intent.getSerializableExtra(Constant.sendObject) as Product
                showDialog(product.image)
            }
            else -> {
                print("Do nothing")
            }
        }

    }

    private fun showDialog(url: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_large_image)
        val imageView = dialog.findViewById(R.id.largeIv) as ImageView
        val file = File(url)
        if (file.exists()) {
            val bitmap = BitmapFactory.decodeFile(file.absolutePath);
            imageView.setImageBitmap(bitmap)
        }
        val yesBtn = dialog.findViewById(R.id.btnOk) as Button
        yesBtn.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

    }

}
