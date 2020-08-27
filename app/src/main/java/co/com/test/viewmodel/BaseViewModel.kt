package co.com.test.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException

/**
 * @desc this class will handle Error Exception
 * @author : Mahesh Vayak
 * @required
 **/

open class BaseViewModel : ViewModel() {

    // define MutableLiveData for emit observer
    val noInternetException = MutableLiveData<String>()


    /**
     * @desc Method will use for handle timeout and connection exception from API.
     * @param type- type for identify API call and  handle error using type
     */
    fun apiException(type: String = ""): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, throwable ->
            when (throwable) {
                is ConnectException, is HttpException, is UnknownHostException -> noInternetException.postValue(
                    type
                )
            }
        }
    }


}