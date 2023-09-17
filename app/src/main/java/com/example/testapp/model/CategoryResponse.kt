package com.example.testapp.model

import com.google.gson.annotations.SerializedName

class CategoryResponse (
    @SerializedName("status"     ) var status     : Boolean?              = null,
    @SerializedName("msg"        ) var msg        : String?               = null,
    @SerializedName("categories" ) var categories : ArrayList<Categories> = arrayListOf()
        )