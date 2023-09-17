package com.example.testapp.repository

import com.example.testapp.api.ApiInterface


class CategoryRepository constructor(private val apiInterface: ApiInterface){

    fun categoryFetch() = apiInterface.categoryFetch()

}