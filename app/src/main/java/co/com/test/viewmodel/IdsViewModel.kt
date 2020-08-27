package co.com.test.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.com.test.constants.AppConstants
import co.com.test.model.IdsModel
import co.com.test.repository.RestInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

/**
 * @desc this class will handle API functions
 * @author : Mahesh Vayak
 * @required
 **/
class IdsViewModel constructor(private val restInterface: RestInterface) : BaseViewModel() {
    val successUserIdsResponse = MutableLiveData<IdsModel>()
    val errorUserIdsResponse = MutableLiveData<ResponseBody>()


    /**
     *  Method will use for get user ids.
     */
    fun getUserList() {
        viewModelScope.launch(apiException() + Dispatchers.Main) {
            val response = restInterface.getIdsList()

            when (response.code()) {
                AppConstants.API_SUCCESS_CODE -> {
                    successUserIdsResponse.postValue(response.body())
                }
                else -> {
                    errorUserIdsResponse.postValue(response.errorBody())
                }

            }

        }
    }

    /**
     * Clears the [ViewModel] when the [Fragment] or [Activity] is not visible to user.
     */
    fun onDetach() {
        viewModelScope.cancel()
    }


}