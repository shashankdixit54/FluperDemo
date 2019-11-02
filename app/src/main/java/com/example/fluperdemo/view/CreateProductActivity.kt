package com.example.fluperdemo.view

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.fluperdemo.Constant
import com.example.fluperdemo.R
import androidx.lifecycle.Observer
import com.example.fluperdemo.adapter.ColorSpinnerAdapter
import com.example.fluperdemo.model.Product
import com.example.fluperdemo.model.SpinnerStateModel
import com.example.fluperdemo.model.StoresModel
import com.example.fluperdemo.viewmodel.ProductViewModel
import java.io.File
import java.util.*
import kotlin.collections.ArrayList


class CreateProductActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var colorSpinner: Spinner
    private val listOfColor =
        arrayOf("Black", "Green", "Red", "White", "Yellow", "Orange", "Purple")
    private lateinit var productIdEt: EditText
    private lateinit var nameEt: EditText
    private lateinit var descriptionEt: EditText
    private lateinit var regularPriceEt: EditText
    private lateinit var salePriceEt: EditText
    private lateinit var productIv: ImageView
    private var photoUri: String = ""
    private lateinit var submitBtn: Button
    private lateinit var resetBtn: Button
    private lateinit var colorAdapter: ColorSpinnerAdapter
    private val SELECT_PICTURE = 101
    private val MY_PERMISSIONS_REQUEST_READ_STORAGE = 102
    private lateinit var productViewModel: ProductViewModel
    private var storeslist = arrayListOf<StoresModel>()
    private var colorlist = arrayListOf<String>()
    private lateinit var tvAddedStores: TextView
    private lateinit var ivAddStore: ImageView
    private lateinit var tvHeader: TextView
    private lateinit var productList: ArrayList<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_product)

        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        productViewModel.allProducts.observe(this, Observer { products ->

            productList = products as ArrayList<Product>
        })

        colorSpinner = findViewById<Spinner>(R.id.spinnerColor)
        productIdEt = findViewById<EditText>(R.id.etProductId)
        nameEt = findViewById<EditText>(R.id.etProductName)
        descriptionEt = findViewById<EditText>(R.id.etDescription)
        regularPriceEt = findViewById<EditText>(R.id.etRegularPrice)
        salePriceEt = findViewById<EditText>(R.id.etSalePrice)
        productIv = findViewById<ImageView>(R.id.ivProduct)
        submitBtn = findViewById<Button>(R.id.btnInsertData)
        tvAddedStores = findViewById<TextView>(R.id.tvAddedStores)
        ivAddStore = findViewById<ImageView>(R.id.addStoreImageView)
        resetBtn = findViewById<Button>(R.id.btnReset)
        tvHeader = findViewById<TextView>(R.id.headerTvCreateProduct)
        submitBtn.setOnClickListener(this)
        resetBtn.setOnClickListener(this)
        productIv.setOnClickListener(this)
        ivAddStore.setOnClickListener(this)


        if (intent.getStringExtra(Constant.selection).equals(Constant.updatePage)) {
            val product = intent.getSerializableExtra(Constant.sendObject) as Product
            if (null != product)
                setAllData(product)
            submitBtn.setText(getString(R.string.update))
            resetBtn.visibility = View.GONE
            productIdEt.isEnabled = false
            tvHeader.text = getString(R.string.update_product)
        } else {
            val randomId = generateRandomId()
            Log.e("Random Id ", randomId.toString())
            submitBtn.setText(getString(R.string.submit))
            resetBtn.visibility = View.VISIBLE
            //productIdEt.setText(randomId.toString())
            //productIdEt.isEnabled = false
            tvHeader.text = getString(R.string.create_product)
            setColorAdapter()
        }

    }

    private fun setAllData(product: Product) {

        productIdEt.setText(product.id.toString())
        nameEt.setText(product.name.toString())
        descriptionEt.setText(product.description.toString())

        regularPriceEt.setText(product.regularPrice.toString())

        salePriceEt.setText(product.salePrice.toString())

        colorlist.clear()
        colorlist.addAll(product.colors)

        storeslist.clear()
        storeslist.addAll(product.stores)
        setColorAdapter()

        val storeString: StringBuffer = StringBuffer()
        for (i in 0 until storeslist.size) {
            storeString.append(storeslist[i].storeName).append("\n")
                .append(storeslist[i].storeAddress).append("\n\n")
        }
        tvAddedStores.text = storeString.toString()

        photoUri = product.image
        if (null != photoUri) {
            val file = File(photoUri)
            if (file.exists()) {
                val bitmap = BitmapFactory.decodeFile(file.absolutePath);
                productIv.setImageBitmap(bitmap)
            }
        }
    }


    private fun setColorAdapter() {
        val list = arrayListOf<SpinnerStateModel>()
        for (x in 0 until listOfColor.size) {
            if (colorlist.size > 0) {
                for (i in 0 until colorlist.size) {
                    if (colorlist[i].equals(listOfColor[x])) {
                        val s = SpinnerStateModel(listOfColor[x], true)
                        list.add(s)
                        break
                    } else {
                        val s = SpinnerStateModel(listOfColor[x], false)
                        list.add(s)
                        break
                    }

                }
            } else {
                val s = SpinnerStateModel(listOfColor[x], false)
                list.add(s)
            }

        }
        colorAdapter = ColorSpinnerAdapter(this@CreateProductActivity, list)
        colorSpinner.adapter = colorAdapter
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == Activity.RESULT_OK && requestCode == SELECT_PICTURE && null != data) {

            val selectedImageUri = data.getData()

            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)

            val cursor = contentResolver.query(
                selectedImageUri,
                filePathColumn, null, null, null
            )
            cursor!!.moveToFirst()

            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
            val picturePath = cursor.getString(columnIndex)
            cursor.close()

            photoUri = picturePath
            val file = File(photoUri)
            if (file.exists()) {
                val bitmap = BitmapFactory.decodeFile(file.absolutePath);
                productIv.setImageBitmap(bitmap)

            }

        } else if (resultCode == Activity.RESULT_OK && requestCode == MY_PERMISSIONS_REQUEST_READ_STORAGE && null != data) {
            openImagePicker()
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun checkStoragePermission(): Boolean {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }
        return true
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            submitBtn.id -> {
                // To take the store value selected from color spinner
                for (i in 0 until colorAdapter.modelArrayList.size) {
                    if (colorAdapter.modelArrayList[i].selected) {
                        colorlist.add(colorAdapter.modelArrayList[i].title)
                    }
                }

                if (productIdEt.text.isEmpty() || nameEt.text.isEmpty() || descriptionEt.text.isEmpty()
                    || regularPriceEt.text.isEmpty() || salePriceEt.text.isEmpty()
                    || colorlist.size <= 0 || storeslist.size <= 0 || photoUri.isEmpty()
                ) {

                    Toast.makeText(
                        this@CreateProductActivity,
                        getString(R.string.empty_field),
                        Toast.LENGTH_LONG
                    ).show()

                } else {
                    // inserting or updating product into database

                    val product = Product(
                        productIdEt.text.toString().toInt(),
                        nameEt.text.toString(),
                        descriptionEt.text.toString(),
                        regularPriceEt.text.toString().toDouble(),
                        salePriceEt.text.toString().toDouble(),
                        photoUri,
                        colorlist,
                        storeslist
                    )

                    if (intent.getStringExtra(Constant.selection) == Constant.updatePage) {
                        productViewModel.update(
                            product
                        )
                        Toast.makeText(
                            this@CreateProductActivity,
                            getString(R.string.product_updated), Toast.LENGTH_LONG
                        ).show()
                        finish()
                    } else {

                        var isExist = false
                        for(i in 0 until productList.size){
                            if(productList[i].id == productIdEt.text.toString().toInt()){
                                isExist = true
                            }
                        }
                        if (isExist){
                            Toast.makeText(
                                this@CreateProductActivity,
                                getString(R.string.product_id_exist), Toast.LENGTH_LONG
                            ).show()
                        }else{
                            productViewModel.insert(product)
                            Toast.makeText(
                                this@CreateProductActivity,
                                getString(R.string.product_inserted), Toast.LENGTH_LONG
                            ).show()
                            finish()
                        }

                    }

                }
            }
            resetBtn.id -> {

                nameEt.setText("")
                descriptionEt.setText("")
                regularPriceEt.setText("")
                salePriceEt.setText("")
                tvAddedStores.setText("")
                colorlist.clear()
                storeslist.clear()
                photoUri = ""
                productIv.setImageBitmap(null)
                setColorAdapter()

            }

            productIv.id -> {

                if (!checkStoragePermission()) {
                    requestPermission()
                } else {
                    openImagePicker()
                }

            }

            ivAddStore.id -> {
                showDialog()
            }
            else -> {
                print("Do nothing")
            }
        }

    }

    private fun openImagePicker() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(
                intent,
                Constant.selectPicture
            ), SELECT_PICTURE
        )

    }

    private fun requestPermission() {

        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            MY_PERMISSIONS_REQUEST_READ_STORAGE
        )
    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_add_stores)
        val storeName = dialog.findViewById(R.id.storeNameEt) as EditText
        val storeAddress = dialog.findViewById(R.id.etStoreAddress) as EditText
        val yesBtn = dialog.findViewById(R.id.btnSaveStore) as Button
        val noBtn = dialog.findViewById(R.id.btnCancel) as Button
        yesBtn.setOnClickListener {
            if (storeName.text.isEmpty() || storeAddress.text.isEmpty()) {
                Toast.makeText(
                    this@CreateProductActivity,
                    getString(R.string.empty_field), Toast.LENGTH_LONG
                ).show()
            } else {
                val store = StoresModel(storeName.text.toString(), storeAddress.text.toString())
                storeslist.add(store)
                tvAddedStores.text =
                    tvAddedStores.text.toString() + storeName.text.toString() + "\n" + storeAddress.text.toString() + "\n\n"
                dialog.dismiss()
            }

        }
        noBtn.setOnClickListener { dialog.dismiss() }
        dialog.show()

    }

    private fun generateRandomId(): Int {
        val r = Random()
        val i1 = r.nextInt(10000 - 1) + 1
        return i1
    }
}