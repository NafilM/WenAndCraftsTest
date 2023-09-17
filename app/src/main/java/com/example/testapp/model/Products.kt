package com.example.testapp.model

import com.google.gson.annotations.SerializedName

data class Products (
    @SerializedName("title"       ) var title       : String? = null,
    @SerializedName("price"       ) var price       : Int?    = null,
    @SerializedName("imageUrl"    ) var imageUrl    : String? = null,
    @SerializedName("description" ) var description : String? = null
        )