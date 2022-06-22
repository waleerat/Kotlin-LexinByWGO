package com.wgoweb.lexinbywgo.utility.MSP

import android.provider.ContactsContract
import android.util.JsonReader
import android.util.JsonWriter
import com.google.gson.GsonBuilder
import com.wgoweb.lexinbywgo.network.LexikonService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonToken
import com.wgoweb.lexinbywgo.model.LexikonModel
import java.io.IOException
import com.google.gson.Gson





object ServiceBuilder {
    private val client = OkHttpClient.Builder().build()


    private val gson = GsonBuilder()
            .setLenient()
       // .registerTypeAdapter(LexikonModel::class.java, ContentAdapter())
        .create()


    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))

        .client(client)
        .build()

    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }
}

class ContentAdapter : TypeAdapter<LexikonModel?>() {
    @Throws(IOException::class)
    override fun write(out: com.google.gson.stream.JsonWriter?, value: LexikonModel?) {
        TODO("Not yet implemented")
    }

    @Throws(IOException::class)
    override fun read(`in`: com.google.gson.stream.JsonReader?): LexikonModel? {
        if (`in` != null) {
            return if (`in`.peek() !== JsonToken.NULL) {
                fromJson(`in`.nextString())
            } else {
                `in`.nextNull()
                null
            }
        } else {
            return  null
        }
    }


}
