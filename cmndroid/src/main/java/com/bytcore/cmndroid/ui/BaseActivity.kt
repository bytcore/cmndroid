package com.bytcore.cmndroid.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.bytcore.cmndroid.R
import com.bytcore.cmndroid.SAVED_STATE_DATA
import com.bytcore.cmndroid.utils.UiUtils.relaunch
import com.google.android.material.snackbar.Snackbar

/**
 * @author Md Ariful Islam
 * @since 13-Jan-2020
 */
abstract class BaseActivity : AppCompatActivity(), Communicator {

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        getViewModel()?.let { viewModel ->
            outState.putBundle(SAVED_STATE_DATA, viewModel.getSavedStateBundle())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getActivity(): AppCompatActivity {
        return this
    }

    override fun navigateTo(destination: Class<out Activity>, extras: Bundle?, clearTask: Boolean) {
        val intent = Intent(this, destination)
        extras?.let { intent.putExtras(it) }
        if (clearTask) {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }

    override fun navigateTo(destination: Fragment, tag: String?, addToBackStack: Boolean) {
        // empty-implementation
    }

    override fun setToolbarTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun hideSoftKeyboard() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(findViewById<View>(android.R.id.content).windowToken, 0)
    }

    override fun showSnackMessage(
        message: String?,
        anchor: View?,
        action: String?,
        onAction: (() -> Unit)?,
        onDismissed: (() -> Unit)?
    ) {
        message?.let {
            val snackBar = Snackbar.make(anchor ?: getContentView(), message, Snackbar.LENGTH_LONG)
            snackBar.addCallback(object : Snackbar.Callback() {
                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    onDismissed?.invoke()
                }
            })

            if (action != null) {
                snackBar.setAction(action) { onAction?.invoke() }
            }

            snackBar.show()
        }
    }

    override fun showToastMessage(message: String?) {
        message?.let { Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show() }
    }

    protected open fun getViewModel(): BaseViewModel? {
        return null
    }

    protected fun retainState(savedInstanceState: Bundle) {
        savedInstanceState.getBundle(SAVED_STATE_DATA)?.let { bundle ->
            getViewModel()?.onRetainState(bundle)
        }
    }

    protected fun setToolbar(
        toolbar: Toolbar,
        title: String? = null,
        showUpButton: Boolean = false
    ) {
        setSupportActionBar(toolbar)
        supportActionBar?.title = title

        if (showUpButton) {
            supportActionBar?.setDisplayShowHomeEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    protected fun observeCommonData(rootView: CoordinatorLayout? = null) {
        getViewModel()?.onConnectionError()?.observe(this, { showOfflineMessage() })

        getViewModel()?.onMessageChanged()?.observe(this, { message ->
            message?.let { showSnackMessage(message = message.text, anchor = rootView) }
        })

        getViewModel()?.onUnknownError()?.observe(this, {
            showSnackMessage(
                message = getString(R.string.error_msg_unknown_error),
                anchor = rootView
            )
        })

        getViewModel()?.onUnauthorized()?.observe(this, {
            showSnackMessage(
                message = getString(R.string.error_msg_unauthorized),
                anchor = rootView,
                action = getString(R.string.btn_login_again),
                onDismissed = { relaunch(this@BaseActivity) })
        })
    }

    protected fun showOfflineMessage() {
        showSnackMessage(getString(R.string.error_msg_offline))
    }

    private fun getContentView(): View {
        return findViewById(android.R.id.content)
    }
}
