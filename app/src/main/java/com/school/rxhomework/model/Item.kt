package com.school.rxhomework.model

import com.google.gson.annotations.SerializedName

data class Item(
        @SerializedName("id")
        val id: Long,
        @SerializedName("title")
        val title: String,
        @SerializedName("body")
        val body: String
)