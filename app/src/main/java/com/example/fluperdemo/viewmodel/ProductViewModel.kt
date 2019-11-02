package com.example.fluperdemo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.fluperdemo.ProductRepository
import com.example.fluperdemo.db.ProductRoomDatabase
import com.example.fluperdemo.model.Product
import com.example.fluperdemo.model.StoresModel
import kotlinx.coroutines.launch

class ProductViewModel (application: Application) : AndroidViewModel(application) {

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: ProductRepository
    // LiveData gives us updated Products when they change.
    val allProducts: LiveData<List<Product>>

    init {
        // Gets reference to ProductDao from ProductRoomDatabase to construct
        // the correct ProductRepository.
        val productDao = ProductRoomDatabase.getDatabase(application).productDao()
        repository = ProductRepository(productDao)
        allProducts = repository.allWords
    }

    fun insert(product: Product) = viewModelScope.launch {
        repository.insert(product)
    }

    fun delete(id: Int) = viewModelScope.launch {
        repository.deleteProductById(id)
    }

    fun update(product: Product) = viewModelScope.launch {
        repository.updateProductById(product)
    }


}