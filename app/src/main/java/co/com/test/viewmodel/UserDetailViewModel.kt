package co.com.test.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.com.test.constants.AppConstants
import co.com.test.model.UserDataModel
import co.com.test.repository.RestInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

/**
 * @desc this class will handle API functions and UI function
 * @author : Mahesh Vayak
 * @required
 **/

class UserDetailViewModel constructor(private val restInterface: RestInterface) : BaseViewModel() {

    val successUserDetailResponse = MutableLiveData<UserDataModel>()
    val errorUserDetailResponse = MutableLiveData<ResponseBody>()
    val updateDetailUI = MutableLiveData<UserDataModel.DataModel>()

    /**
     *  Method will use for get user detail from id.
     *  @param id - user id
     */
    fun getUserDetail(id: String) {
        viewModelScope.launch(apiException() + Dispatchers.Main) {
            val response = restInterface.getUserDetail(id)

            when (response.code()) {
                AppConstants.API_SUCCESS_CODE -> {
                    successUserDetailResponse.postValue(response.body())
                }
                else -> {
                    errorUserDetailResponse.postValue(response.errorBody())
                }

            }

        }
    }


    /**
     *  Method will use for display data in UI
     *  @param id - user id
     */
    fun updateDetailUI(data:UserDataModel.DataModel){
        updateDetailUI.postValue(data)
    }
    /**
     * Clears the [ViewModel] when the [Fragment] or [Activity] is not visible to user.
     */
    fun onDetach() {
        viewModelScope.cancel()
    }
}