package co.com.test.repository


import co.com.test.model.IdsModel
import co.com.test.model.UserDataModel
import retrofit2.Response
import retrofit2.http.*

/**
 * @desc Rest API interface handle all API endpoints
 * @author : Mahesh Vayak
 * @required
 **/
interface RestInterface {
    /**
     * @desc get list of ids from API.
     **/
    @GET("list")
    suspend fun getIdsList(): Response<IdsModel>

    /**
     * @desc get user details from API
     * @param id- pass selected user id
     **/
    @GET("get/{id}")
    suspend fun getUserDetail(@Path("id") id:String): Response<UserDataModel>





}
