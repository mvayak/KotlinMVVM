package co.com.test.view.user_detail

import android.content.IntentFilter
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import co.com.test.R

import co.com.test.constants.AppConstants
import co.com.test.extention.beGone
import co.com.test.extention.beVisible
import co.com.test.extention.isOnline
import co.com.test.receiver.ConnectivityReceiver
import co.com.test.view.BaseActivity
import co.com.test.viewmodel.UserDetailViewModel
import kotlinx.android.synthetic.main.context_data_screen.*
import kotlinx.android.synthetic.main.empty_view.*
import kotlinx.android.synthetic.main.progress_dialog_view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @desc this is class will show user details
 * @author : Mahesh Vayak
 * @required
 **/
class UserDetailActivity : BaseActivity(), ConnectivityReceiver.ConnectivityReceiverListener {
    private val mViewModel: UserDetailViewModel by viewModel()
    private var id: String = ""
    private var isResponseSuccess: Boolean = false
    private var connectivityReceiver = ConnectivityReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
        getIntentData()
        listenToViewModel()

    }

    /**
     * @desc get selected user id from intent
     **/
    private fun getIntentData() {
        intent.extras?.apply {
            id = this.getString("id").toString()
        }
    }

    /**
     * @desc this method will use for manage API success and failure,Internet connectivity, make json object for API and un authorization.
     */
    private fun listenToViewModel() {
        mViewModel.successUserDetailResponse.observe(this, Observer {
            isResponseSuccess = true
            it?.data?.apply {
                mViewModel.updateDetailUI(this)
            }
        })

        mViewModel.updateDetailUI.observe(this, Observer {
            it?.apply {
                textViewFirstName.text = it.firstName?.let { it } ?: "-"
                textViewLastName.text = it.lastName?.let { it } ?: "-"
                textViewAge.text = it.age?.let { it.toString() } ?: "-"
                textViewGender.text = it.gender?.let { it } ?: "-"
                textViewCountry.text = it.country?.let { it } ?: "-"
            }
            linearLayoutProgressBar.beGone()
            constraintLayoutContent.beVisible()
            frameLayoutNoData.beGone()
        })

        mViewModel.errorUserDetailResponse.observe(this, Observer {
            linearLayoutProgressBar.beGone()
            constraintLayoutContent.beGone()
            frameLayoutNoData.beVisible()
            textViewNoDataMsg.text = resources.getString(R.string.something_went_wrong)
            isResponseSuccess = false
        })

        mViewModel.noInternetException.observe(this, Observer {
            linearLayoutProgressBar.beGone()
            constraintLayoutContent.beGone()
            frameLayoutNoData.beVisible()
            isResponseSuccess = false
            if (isOnline()) {
                textViewNoDataMsg.text = resources.getString(R.string.something_went_wrong)
            } else {
                textViewNoDataMsg.text = resources.getString(R.string.no_internet_connection)
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * @desc check internet connection. this method will call when internet come and gone
     * @param isConnected (true if internet available and false if internet not available )
     */
    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        if (isConnected && !isResponseSuccess) {
            linearLayoutProgressBar.beVisible()
            constraintLayoutContent.beGone()
            frameLayoutNoData.beGone()
            mViewModel.getUserDetail(id)
        } else if (!isConnected && !isResponseSuccess) {
            linearLayoutProgressBar.beGone()
            constraintLayoutContent.beGone()
            frameLayoutNoData.beVisible()
            textViewNoDataMsg.text = resources.getString(R.string.no_internet_connection)
        }
    }


    /**
     * @desc Register internet connection receiver
     */
    override fun onResume() {
        super.onResume()
        registerReceiver(
            connectivityReceiver,
            IntentFilter(AppConstants.CONNECTION_ACTION)
        )
        ConnectivityReceiver.connectivityReceiverListener = this
    }


    /**
     * @desc Unregister internet connection receiver
     */
    override fun onPause() {
        super.onPause()
        ConnectivityReceiver.connectivityReceiverListener?.apply {
            unregisterReceiver(connectivityReceiver)
        }
    }

    /**
     * @desc stop apis call and clear view model
     */
    override fun onDestroy() {
        super.onDestroy()
        mViewModel.onDetach()
    }

}