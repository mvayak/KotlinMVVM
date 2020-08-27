package co.com.test.view

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.com.test.R


open class BaseActivity : AppCompatActivity() {
    lateinit var progressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showProgressDialog()
    }


    private fun showProgressDialog(): Dialog {
        progressDialog = Dialog(this)
        progressDialog.setContentView(R.layout.progress_dialog_view)
        progressDialog.setCancelable(false)
        progressDialog.window?.setBackgroundDrawable(ColorDrawable(0))
        return progressDialog
    }

    fun launchProgressDialog() {
        if (::progressDialog.isInitialized && progressDialog != null && !progressDialog.isShowing)
            progressDialog.show()
    }

    /*
 * Hide progress bar when doing api call or heavy task on main thread
 */
    fun dismissProgressDialog() {
        if (::progressDialog.isInitialized && progressDialog != null && progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

    override fun onDestroy() {
        dismissProgressDialog()
        super.onDestroy()
    }
}
