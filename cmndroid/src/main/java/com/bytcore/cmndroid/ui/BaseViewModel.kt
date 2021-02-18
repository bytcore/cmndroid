package com.bytcore.cmndroid.ui

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bytcore.cmndroid.EventLiveData
import com.bytcore.cmndroid.exceptions.UnauthorizedException
import java.io.IOException

/**
 * @author Md Ariful Islam
 * @since 15-Jan-2020
 */
abstract class BaseViewModel : ViewModel() {

    protected val loaderState: MutableLiveData<LoaderState> by lazy { MutableLiveData<LoaderState>() }
    protected val message = EventLiveData<Message>()
    protected val unauthorized = EventLiveData<Unit>()
    private val connectionError = EventLiveData<Unit>()
    private val unknownError = EventLiveData<Unit>()

    abstract fun getSavedStateBundle(): Bundle

    abstract fun onRetainState(bundle: Bundle)

    override fun onCleared() {
        super.onCleared()
    }

    fun onLoadingStateChanged(): LiveData<LoaderState> {
        return loaderState
    }

    fun onMessageChanged(): LiveData<Message> {
        return message
    }

    fun onUnauthorized(): LiveData<Unit> {
        return unauthorized
    }

    fun onConnectionError(): LiveData<Unit> {
        return connectionError
    }

    fun onUnknownError(): LiveData<Unit> {
        return unknownError
    }

    protected fun showUnknownError() {
        unknownError.value = Unit
    }

    protected fun catchCommonError(error: Throwable): Boolean {
        if (error is UnauthorizedException) {
            unauthorized.value = Unit
            return true
        }

        if (error is IOException) {
            connectionError.value = Unit
            return true
        }

        return false
    }
}
