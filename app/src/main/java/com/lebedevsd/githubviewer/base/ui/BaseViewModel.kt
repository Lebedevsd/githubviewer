package com.lebedevsd.githubviewer.base.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

import timber.log.Timber

abstract class BaseViewModel<T>(
    protected val handle: SavedStateHandle
) : ViewModel(){

    val lce: LiveData<LCE<T>>
        get() = _loadingState

    private val _loadingState = MutableLiveData<LCE<T>>()

    protected fun notifyLoading(content: T) {
        _loadingState.postValue(LCE.Loading(content))
    }

    protected fun notifyContentLoaded(content: T) {
        _loadingState.postValue(LCE.Content(content))
    }

    protected fun notifyError(throwable: Throwable, content: T) {
        _loadingState.postValue(LCE.Error(throwable, content))
    }

    fun retryAfterError(throwable: Throwable): Boolean {
        // Always retry no matter what is the error
        // Optionally, throwable can be inspected to determine which error to retry
        return true
    }

    override fun onCleared() {
        super.onCleared()
        Timber.d("ViewModel is destroyed")
    }
}

sealed class LCE<T>(val content: T) {
    class Error<T>(val throwable: Throwable, content: T) : LCE<T>(content)
    class Loading<T>(content: T) : LCE<T>(content)
    class Content<T>(content: T) : LCE<T>(content)

    fun isLoading(): Boolean = this is Loading
}
