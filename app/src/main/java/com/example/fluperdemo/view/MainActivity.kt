package com.example.fluperdemo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fluperdemo.Constant
import com.example.fluperdemo.R
import com.example.fluperdemo.model.Product
import com.example.fluperdemo.viewmodel.ProductViewModel

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var btnCreate: Button
    private lateinit var btnShow: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnCreate = findViewById(R.id.btnCreate)
        btnShow = findViewById(R.id.btnShow)

        btnCreate.setOnClickListener(this)
        btnShow.setOnClickListener(this)

    }

    override fun onClick(v: View?) {

        when (v?.id) {
            btnCreate.id -> {

                val intent = Intent(this,CreateProductActivity::class.java)
                intent.putExtra(Constant.selection,Constant.homePage)
                startActivity(intent)

            }
            btnShow.id -> {

                val intent = Intent(this,ShowProductActivity::class.java)
                startActivity(intent)

            }
            else -> {
                print("Do nothing")
            }
        }

    }


}
