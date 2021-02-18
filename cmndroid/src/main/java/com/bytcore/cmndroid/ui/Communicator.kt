package com.bytcore.cmndroid.ui

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

/**
 * @author Md Ariful Islam
 * @since 13-Jan-2020
 */
interface Communicator {

    fun getActivity(): AppCompatActivity

    fun navigateTo(
        destination: Class<out Activity>,
        extras: Bundle? = null,
        clearTask: Boolean = false
    )

    fun navigateTo(destination: Fragment, tag: String? = null, addToBackStack: Boolean = false)

    fun setToolbarTitle(title: String)

    fun hideSoftKeyboard()

    fun showSnackMessage(
        message: String? = null,
        anchor: View? = null,
        action: String? = null,
        onAction: (() -> Unit)? = null,
        onDismissed: (() -> Unit)? = null
    )

    fun showToastMessage(message: String? = null)
}
