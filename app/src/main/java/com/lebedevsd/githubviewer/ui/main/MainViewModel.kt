package com.lebedevsd.githubviewer.ui.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.lebedevsd.githubviewer.di.ViewModelAssistedFactory
import com.lebedevsd.githubviewer.di.scheduler.UiScheduler
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.Scheduler

class MainViewModel @AssistedInject constructor(
    @Assisted handle: SavedStateHandle,
    @UiScheduler val uiScheduler: Scheduler
): ViewModel() {


    @AssistedInject.Factory
    interface Factory : ViewModelAssistedFactory<MainViewModel>
}
