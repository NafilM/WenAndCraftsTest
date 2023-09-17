package com.example.testapp.model

import com.google.gson.annotations.SerializedName

data class Categories (
    @SerializedName("title"    ) var title    : String?             = null,
    @SerializedName("products" ) var products : ArrayList<Products> = arrayListOf()
        )