package com.lebedevsd.githubviewer.ui.searchrepos;

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
public abstract class SearchReposModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchReposViewModel.class)
    abstract ViewModelAssistedFactory<? extends ViewModel> bindFactory(SearchReposViewModel.Factory factory);

    @Binds
    abstract SavedStateRegistryOwner bindSavedStateRegistryOwner(SearchReposFragment searchReposFragment);

    @Nullable
    @Provides
    static Bundle provideDefaultArgs() {
        return null;
    }

    @Binds
    abstract ViewStateEpoxyController<SearchReposViewState> bindViewStateEpoxyController(SearchReposController controller);
}