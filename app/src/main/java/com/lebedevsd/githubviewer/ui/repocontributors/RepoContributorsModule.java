package com.lebedevsd.githubviewer.ui.repocontributors;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.savedstate.SavedStateRegistryOwner;

import com.lebedevsd.githubviewer.base.epoxy.ViewStateEpoxyController;
import com.lebedevsd.githubviewer.di.ViewModelAssistedFactory;
import com.lebedevsd.githubviewer.di.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

@Module
public abstract class RepoContributorsModule {

    @Binds
    @IntoMap
    @ViewModelKey(RepoContributorsViewModel.class)
    abstract ViewModelAssistedFactory<? extends ViewModel> bindFactory(RepoContributorsViewModel.Factory factory);

    @Binds
    abstract SavedStateRegistryOwner bindSavedStateRegistryOwner(RepoContributorsFragment repoContributorsFragment);

    @Nullable
    @Provides
    static Bundle provideDefaultArgs() {
        return null;
    }

    @Binds
    abstract ViewStateEpoxyController<RepoContributorsViewState> bindViewStateEpoxyController(RepoContributorsController controller);
}