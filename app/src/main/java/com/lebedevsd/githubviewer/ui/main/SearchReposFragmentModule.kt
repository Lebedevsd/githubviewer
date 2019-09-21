package com.lebedevsd.githubviewer.ui.main

import com.lebedevsd.githubviewer.di.PerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SearchReposFragmentModule {

    @PerFragment
    @ContributesAndroidInjector(modules = [SearchReposModule::class])
    abstract fun contributeMainFragmentInjector(): SearchReposFragment
}