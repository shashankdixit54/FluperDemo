package com.example.fluperdemo.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.fluperdemo.model.Product
import com.example.fluperdemo.model.StoresModel
import java.util.ArrayList

@Dao
interface ProductDao {

    @Query("SELECT * from product_table")
    fun getAllProduct(): LiveData<List<Product>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(product: Product)

    @Query("DELETE FROM product_table WHERE product_id LIKE :productId")
    suspend fun deleteProductById(productId : Int)

    @Query("SELECT * from product_table WHERE product_id LIKE :productId")
    suspend fun getProductById(productId : Int): List<Product>

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(product: Product )

}