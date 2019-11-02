package com.example.fluperdemo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.fluperdemo.db.ColorConverters
import com.example.fluperdemo.db.StoresConverters
import java.io.Serializable
import java.util.ArrayList

@Entity(tableName = "product_table")
@TypeConverters(ColorConverters::class, StoresConverters::class)
data class Product(@PrimaryKey @ColumnInfo(name = "product_id") val id: Int,
                   @ColumnInfo(name = "product_name") val name: String,
                   @ColumnInfo(name = "description") val description: String,
                   @ColumnInfo(name = "regular_price") val regularPrice: Double,
                   @ColumnInfo(name = "sale_price") val salePrice:Double,
                   @ColumnInfo(name = "image") val image : String,
                   @ColumnInfo(name = "colors") val colors: ArrayList<String>,
                   @ColumnInfo(name = "stores")  val stores : ArrayList<StoresModel>) : Serializable