package com.example.fluperdemo.db

import androidx.room.TypeConverter
import com.example.fluperdemo.model.StoresModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StoresConverters {

    @TypeConverter
    fun fromStoreStringToArrayList(value: String): ArrayList<StoresModel> {
        val listType = object : TypeToken<ArrayList<StoresModel>>() {

        }.type

        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun arrayListStoreToString(cl: ArrayList<StoresModel>): String {

        val gson = Gson()
        return gson.toJson(cl)

    }

}