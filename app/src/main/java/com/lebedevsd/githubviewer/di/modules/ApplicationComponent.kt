package com.lebedevsd.githubviewer.di.modules

import com.lebedevsd.githubviewer.GithubViewerApplication
import com.lebedevsd.githubviewer.di.modules.activities.MainActivityModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AndroidModule::class,
    ViewModelAssistedFactoriesModule::class,
    MainActivityModule::class
])
interface ApplicationComponent : AndroidInjector<GithubViewerApplication> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<GithubViewerApplication>()
}
