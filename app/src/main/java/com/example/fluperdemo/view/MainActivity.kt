package com.example.fluperdemo.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.fluperdemo.Constant
import com.example.fluperdemo.R

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
