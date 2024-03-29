package com.lebedevsd.githubviewer.ui.searchrepos

import com.lebedevsd.githubviewer.di.PerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SearchReposFragmentModule {

    @PerFragment
    @ContributesAndroidInjector(modules = [SearchReposModule::class])
    abstract fun contributeSearchReposFragmentInjector(): SearchReposFragment
}