package com.wgoweb.lexinbywgo.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LexikonModel (
    @SerializedName("Status") val status: String,
   @SerializedName("Corrections") val corrections: List<String>?,
   @SerializedName("Result") val result: List<ResultModel>?,
) : Serializable

data class ResultModel (
    @SerializedName("VariantID") val ordId: String,
    @SerializedName("Value") val ord: String,
    @SerializedName("Type") val ordClass: String,
    @SerializedName("Variant") val variant: String?,
    @SerializedName("BaseLang") val baseLang: BaseLangModel?,
    @SerializedName("TargetLang") val targetLang: TargetLangModel?,
) : Serializable

data class BaseLangModel (
    @SerializedName("Phonetic") val phonetic: PhoneticModel?,
    @SerializedName("Inflection") val inflection: List<InflectionModel>?,
    @SerializedName("Meaning") val meaning: String?,
    @SerializedName("Example")  val example: List<BundleModel>?,
    @SerializedName("Lllustration") val illustration: List<IllustrationModel>?,
    @SerializedName("Idiom") val idiom: List<BundleModel>?,
    @SerializedName("Compound") val compound: List<BundleModel>,
    @SerializedName("Comment") val comment: String?,
) : Serializable


data class TargetLangModel (
    @SerializedName("Comment") val comment: String?,
    @SerializedName("Translation") val translation: String,
    @SerializedName("Idiom") val idiom: List<BundleModel>?,
    @SerializedName("Compound") val compound: List<BundleModel>?,
    @SerializedName("Example") val example: List<BundleModel>?,
) : Serializable


data class PhoneticModel (
    @SerializedName("File") val audioUrl: String,
    @SerializedName("Content") val content: String
    ) : Serializable

data class InflectionModel (
    //@SerializedName("Id") val id: String,
    @SerializedName("Content") val content: String
    ) : Serializable

data class IllustrationModel (
    //@SerializedName("Id") val id: String,
    @SerializedName("Type") val type: String,
    @SerializedName("Value") val value: String
    ) : Serializable

data class BundleModel (
    @SerializedName("Content") val content: String,
    @SerializedName("ID") val id: String
    ) : Serializable