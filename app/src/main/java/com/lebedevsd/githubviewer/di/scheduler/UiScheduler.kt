package com.lebedevsd.githubviewer.di.scheduler

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

import javax.inject.Qualifier


/**
 * Scheduler that will be used for UI work i.e. MainThread
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
annotation class UiScheduler
