package com.lebedevsd.githubviewer.ui.main

import com.lebedevsd.githubviewer.di.PerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentModule {

    @PerFragment
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun contributeMainFragmentInjector(): MainFragment
}