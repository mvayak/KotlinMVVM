package co.com.test.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class IdsModel {
    @SerializedName("status")
    @Expose
    val status: String? = null

    @SerializedName("data")
    @Expose
    val data: MutableList<String>? = null
}
