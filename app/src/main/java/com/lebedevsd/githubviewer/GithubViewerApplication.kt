package com.lebedevsd.githubviewer

import com.lebedevsd.githubviewer.di.modules.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class GithubViewerApplication: DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.builder().create(this)
    }
}
