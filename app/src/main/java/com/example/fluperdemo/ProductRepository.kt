package com.example.fluperdemo

import androidx.lifecycle.LiveData
import com.example.fluperdemo.db.ProductDao
import com.example.fluperdemo.model.Product
import com.example.fluperdemo.model.StoresModel

class ProductRepository(private val productDao: ProductDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allWords: LiveData<List<Product>> = productDao.getAllProduct()

    suspend fun insert(product: Product) {
        productDao.insert(product)
    }

    suspend fun getProductById(id: Int) {
        productDao.getProductById(id)
    }

    suspend fun deleteProductById(id: Int) {
        productDao.deleteProductById(id)
    }

    suspend fun updateProductById(product: Product) {
        productDao.update(product)
    }


}