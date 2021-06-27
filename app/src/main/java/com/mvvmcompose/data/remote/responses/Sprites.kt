package com.mvvmcompose.data.remote.responses

import com.google.gson.annotations.SerializedName

data class Sprites(
    val back_default: String,
    val back_female: Any,
    val back_shiny: String,
    val back_shiny_female: Any,

    @SerializedName("front_default")
    val frontDefault: String,

    val front_female: Any,
    val front_shiny: String,
    val front_shiny_female: Any,
    val other: Other,
    val versions: Versions
)