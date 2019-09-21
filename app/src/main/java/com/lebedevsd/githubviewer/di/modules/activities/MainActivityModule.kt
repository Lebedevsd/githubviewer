package com.lebedevsd.githubviewer.di.modules.activities

import com.lebedevsd.githubviewer.MainActivity
import com.lebedevsd.githubviewer.di.PerActivity
import com.lebedevsd.githubviewer.ui.main.SearchReposFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {

    @PerActivity
    @ContributesAndroidInjector(modules = [MainViewModule::class])
    internal abstract fun contributeMainActivity(): MainActivity

    @Module(includes = [SearchReposFragmentModule::class])
    internal abstract class MainViewModule
}