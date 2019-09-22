package com.lebedevsd.githubviewer.ui.repocontributors

import com.lebedevsd.githubviewer.di.PerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class RepoContributorsFragmentModule {

    @PerFragment
    @ContributesAndroidInjector(modules = [RepoContributorsModule::class])
    abstract fun contributeRepoCollaboratorsFragmentInjector(): RepoContributorsFragment
}