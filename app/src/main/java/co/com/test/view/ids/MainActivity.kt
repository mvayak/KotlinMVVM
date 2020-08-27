package co.com.test.view.ids

import android.content.IntentFilter
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import co.com.test.R
import co.com.test.constants.AppConstants
import co.com.test.extention.beGone
import co.com.test.extention.beVisible
import co.com.test.extention.isOnline
import co.com.test.extention.startActivityInline
import co.com.test.receiver.ConnectivityReceiver
import co.com.test.view.BaseActivity
import co.com.test.view.user_detail.UserDetailActivity
import co.com.test.viewmodel.IdsViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.empty_view.*
import kotlinx.android.synthetic.main.progress_dialog_view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @desc this is class will show list of IDs
 * @author : Mahesh Vayak
 * @required
 **/
class MainActivity : BaseActivity(), ConnectivityReceiver.ConnectivityReceiverListener {
    private val mViewModel: IdsViewModel by viewModel()
    private var idsList: MutableList<String> = mutableListOf()
    private lateinit var idsAdapter: IdsAdapter
    private var connectivityReceiver = ConnectivityReceiver()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        listenToViewModel()
    }

    /**
     * @desc Initialize view
     */
    private fun initView() {
        recyclerViewSearchResults.layoutManager = LinearLayoutManager(this)
        idsAdapter = IdsAdapter(idsList, onItemClick = ::onItemClick)
        recyclerViewSearchResults.adapter = idsAdapter
    }

    /**
     * @desc method will call when tap on user is from adapter and redirect to detail screen
     * @param id- user id
     */
    private fun onItemClick(id: String) {
        val bundle = Bundle()
        bundle.putString("id", id)
        startActivityInline<UserDetailActivity>(bundle)
    }

    /**
     * @desc this method will use for manage API success and failure,Internet connectivity, make json object for API and un authorization.
     */
    private fun listenToViewModel() {
        mViewModel.successUserIdsResponse.observe(this, Observer {
            linearLayoutProgressBar.beGone()
            it?.data?.apply {
                idsList.addAll(this)
                idsAdapter.notifyDataSetChanged()
            }
        })

        mViewModel.errorUserIdsResponse.observe(this, Observer {
            linearLayoutProgressBar.beGone()
            frameLayoutNoData.beVisible()
            textViewNoDataMsg.text = resources.getString(R.string.something_went_wrong)
        })

        mViewModel.noInternetException.observe(this, Observer {
            linearLayoutProgressBar.beGone()
            if (isOnline()) {
                frameLayoutNoData.beVisible()
                textViewNoDataMsg.text = resources.getString(R.string.something_went_wrong)
            } else {
                frameLayoutNoData.beVisible()
                textViewNoDataMsg.text = resources.getString(R.string.no_internet_connection)
            }
        })
    }

    /**
     * @desc check internet connection. this method will call when internet come and gone
     * @param isConnected (true if internet available and false if internet not available )
     */
    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        if (isConnected && idsList.isNullOrEmpty()) {
            linearLayoutProgressBar.beVisible()
            frameLayoutNoData.beGone()
            mViewModel.getUserList()
        } else if (!isConnected && idsList.isNullOrEmpty()) {
            frameLayoutNoData.beVisible()
            linearLayoutProgressBar.beGone()
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