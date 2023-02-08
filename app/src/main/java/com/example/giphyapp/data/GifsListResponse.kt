package com.example.giphyapp.data

import com.google.gson.annotations.SerializedName

data class GifsListResponse(
    @SerializedName("data") val data: List<DataObject>
)

data class DataObject(
    @SerializedName("images") val images: DataImage
)

data class DataImage(
    @SerializedName("original") val ogImage: OgImage
)

data class OgImage(
    val url: String,

)
