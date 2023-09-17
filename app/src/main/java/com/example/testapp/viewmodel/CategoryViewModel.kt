package com.example.testapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapp.model.Categories
import com.example.testapp.model.CategoryResponse
import com.example.testapp.repository.CategoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryViewModel (private val repository: CategoryRepository) : ViewModel() {

    val categorySuccess = MutableLiveData<CategoryResponse>()
    val categoryError = SingleLiveEvent<String>()
    private val viewModelScope = CoroutineScope(Dispatchers.IO)

    fun categoryFetch() {
        viewModelScope.launch {
            try {
                val response = repository.categoryFetch()
                response.enqueue(object : Callback<CategoryResponse> {
                    override fun onResponse(call: Call<CategoryResponse>, response: Response<CategoryResponse>) {
                        if (response.isSuccessful){
                            if (response.body() != null){
                                if (response.body()?.status == true){
                                    categorySuccess.postValue(response.body())
                                }else{
                                    categoryError.postValue(response.body()?.msg)
                                }
                            }else {
                                categoryError.postValue("response.body is null")
                            }

                        }else {
                            categoryError.postValue("api not 200")
                        }
                    }

                    override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                        categoryError.postValue(t.message)
                    }
                })
            }catch (t: Throwable) {
                Log.e("api : ", "onFailure")
                t.printStackTrace()
            }
        }
    }
}