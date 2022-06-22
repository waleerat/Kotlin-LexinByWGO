package com.wgoweb.lexinbywgo.network

import com.wgoweb.lexinbywgo.model.LexikonModel
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.Url

import retrofit2.http.GET

interface LexikonService {

    @GET
    open fun getStringResponse(@Url url: String?): Call<String?>?
}
