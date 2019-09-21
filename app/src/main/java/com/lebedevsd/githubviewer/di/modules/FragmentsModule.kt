package com.lebedevsd.githubviewer.di.modules

import com.lebedevsd.githubviewer.ui.main.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentsModule {

    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment
}