package co.com.test.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class UserDataModel {
    @SerializedName("status")
    @Expose
     val status: String? = null

    @SerializedName("data")
    @Expose
     val data: DataModel? = null


    class DataModel{
        @SerializedName("id")
        @Expose
         val id: String? = null

        @SerializedName("firstName")
        @Expose
         val firstName: String? = null

        @SerializedName("lastName")
        @Expose
         val lastName: String? = null

        @SerializedName("age")
        @Expose
         val age: Int? = null

        @SerializedName("gender")
        @Expose
         val gender: String? = null

        @SerializedName("country")
        @Expose
         val country: String? = null
    }

}