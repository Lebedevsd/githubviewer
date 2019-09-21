package com.lebedevsd.githubviewer.di.modules

import android.app.Application
import android.content.Context
import com.lebedevsd.githubviewer.di.scheduler.BackgroundTaskScheduler
import com.lebedevsd.githubviewer.di.scheduler.UiScheduler
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
class AndroidModule {

    @Provides
    @Singleton
    internal fun providesContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    @BackgroundTaskScheduler
    internal fun providesBgScheduler(): Scheduler {
        return Schedulers.io()
    }

    @Provides
    @Singleton
    @UiScheduler
    internal fun providesUiScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

//    @Provides
//    @Singleton
//    internal fun providesLocalApi(appDatabase: AppDatabase): RecipeDao {
//        return appDatabase.recipeDao()
//    }

    @Provides
    @Singleton
    internal fun providesMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides
    @Singleton
    internal fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }
}
