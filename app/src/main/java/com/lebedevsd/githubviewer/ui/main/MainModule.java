package com.lebedevsd.githubviewer.ui.main;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.savedstate.SavedStateRegistryOwner;

import com.lebedevsd.githubviewer.di.ViewModelAssistedFactory;
import com.lebedevsd.githubviewer.di.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModelAssistedFactory<? extends ViewModel> bindFactory(MainViewModel.Factory factory);

    @Binds
    abstract SavedStateRegistryOwner bindSavedStateRegistryOwner(MainFragment commitDetailFragment);

    @Nullable
    @Provides
    static Bundle provideDefaultArgs() {
        return null;
    }
}